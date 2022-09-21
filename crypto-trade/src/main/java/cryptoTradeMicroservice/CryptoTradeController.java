package cryptoTradeMicroservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cryptoTradeMicroservice.dto.BankAccountDto;
import cryptoTradeMicroservice.dto.CryptoWalletDto;
import cryptoTradeMicroservice.dto.CurrencyConversionDto;
import cryptoTradeMicroservice.proxy.BankAccountProxy;
import cryptoTradeMicroservice.proxy.CryptoWalletProxy;
import cryptoTradeMicroservice.proxy.CurrencyConversionProxy;
import cryptoTradeMicroservice.proxy.CurrencyExchangeProxy;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CryptoTradeController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CryptoTradeRepository repo;
	
	@Autowired
	private BankAccountProxy proxyAccount;
	
	@Autowired
	private CryptoWalletProxy proxyWallet;
	
	@Autowired
	private CurrencyConversionProxy proxyConversion;
	
	@GetMapping("/crypto-trade-feign/from/{from}/to/{to}")
	public CryptoTrade getExchange(@PathVariable String from, @PathVariable String to) {
		
		String port = environment.getProperty("local.server.port");
		CryptoTrade temp = repo.findByFromAndTo(from, to);
		
		if(temp==null) {
			throw new NullPointerException("Conversion not found");
		}
		
		return new CryptoTrade(temp.getId(),from,to,temp.getConversionMultiple(),port);

	}

	@RateLimiter(name = "default")
	@GetMapping("/crypto-trade-feign/from/{from}/to/{to}/quantity/{quantity}/user/{user}")
	public ResponseEntity<Object> getTrade(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity, @PathVariable String user) {
		
		BankAccountDto acc = proxyAccount.getAccount(user);
		CryptoWalletDto wal = proxyWallet.getWallet(user);
		
		CryptoTrade temp = new CryptoTrade();
		
		if(from.equals("USD") && acc.getUsd().compareTo(quantity) >= 0 && (to.equals("BTC") || to.equals("ETH") || to.equals("BNB"))) {
			acc.setUsd(acc.getUsd().subtract(quantity));
			temp = this.getExchange(from, to);
			
			BigDecimal value = quantity.multiply(temp.getConversionMultiple());
			
			if(to.equals("BTC")) {
				wal.setBtc(wal.getBtc().add(value));
			} else if (to.equals("ETH")) {
				wal.setEth(wal.getEth().add(value));
			} else if (to.equals("BNB")) {
				wal.setBnb(wal.getBnb().add(value));
			}
			
			proxyAccount.updateAccount(acc);
			proxyWallet.updateWallet(wal);
			
			return new ResponseEntity<Object>(wal, HttpStatus.OK);
			
		} else if(from.equals("EUR") && acc.getEur().compareTo(quantity) >= 0 && (to.equals("BTC") || to.equals("ETH") || to.equals("BNB"))) {
			acc.setEur(acc.getEur().subtract(quantity));
			temp = this.getExchange(from, to);
			
			BigDecimal value = quantity.multiply(temp.getConversionMultiple());
			
			if(to.equals("BTC")) {
				wal.setBtc(wal.getBtc().add(value));
			} else if (to.equals("ETH")) {
				wal.setEth(wal.getEth().add(value));
			} else if (to.equals("BNB")) {
				wal.setBnb(wal.getBnb().add(value));
			}
			
			proxyAccount.updateAccount(acc);
			proxyWallet.updateWallet(wal);
			
			return new ResponseEntity<Object>(wal, HttpStatus.OK);
			
		} else if((from.equals("RSD") || from.equals("CHF") || from.equals("GBP")) && (to.equals("BTC") || to.equals("ETH") || to.equals("BNB"))) {
			
			BankAccountDto conversionAcc = proxyConversion.getConversionAccount(from, "EUR", quantity, user);
			CurrencyConversionDto conversion = proxyConversion.getConversion(from, "EUR" , quantity);
			temp = this.getExchange("EUR", to);
			
			conversionAcc.setEur(conversionAcc.getEur().subtract(conversion.getTotalConversionAmount()));
			BigDecimal value = conversion.getTotalConversionAmount().multiply(temp.getConversionMultiple());
			
			if(to.equals("BTC")) {
				wal.setBtc(wal.getBtc().add(value));
			} else if (to.equals("ETH")) {
				wal.setEth(wal.getEth().add(value));
			} else if (to.equals("BNB")) {
				wal.setBnb(wal.getBnb().add(value));
			}
			
			proxyAccount.updateAccount(conversionAcc);
			proxyWallet.updateWallet(wal);
			
			return new ResponseEntity<Object>(wal, HttpStatus.OK);
			
		} else if (from.equals("BTC") || from.equals("ETH") || from.equals("BNB")) {
			if(from.equals("BTC") && wal.getBtc().compareTo(quantity) >= 0) {
				wal.setBtc(wal.getBtc().subtract(quantity));
			} else if (from.equals("ETH") && wal.getEth().compareTo(quantity) >= 0) {
				wal.setEth(wal.getEth().subtract(quantity));
			} else if (from.equals("BNB") && wal.getBnb().compareTo(quantity) >= 0) {
				wal.setBnb(wal.getBnb().subtract(quantity));
			} else {
				return ResponseEntity.badRequest().body("Try again");
			}
			
			if(to.equals("USD") || to.equals("EUR")) {
				temp = this.getExchange(from, to);
				
				if(to.equals("USD")) {
					acc.setUsd(acc.getUsd().add(quantity.multiply(temp.getConversionMultiple())));
				} else {
					acc.setEur(acc.getEur().add(quantity.multiply(temp.getConversionMultiple())));
				}
			} else if(to.equals("RSD") || to.equals("CHF") || to.equals("GBP")) {
				temp = this.getExchange(from, "EUR");
				quantity = quantity.multiply(temp.getConversionMultiple());
				
				CurrencyConversionDto conversion = proxyConversion.getConversion("EUR", to, quantity);
				BigDecimal value = conversion.getTotalConversionAmount();
						
				if(to.equals("RSD")) {
					acc.setRsd(acc.getRsd().add(value));
				} else if(to.equals("CHF")) {
					acc.setChf(acc.getChf().add(value));
				} else if(to.equals("GBP")) {
					acc.setGbp(acc.getGbp().add(value));
				}
			}
			proxyAccount.updateAccount(acc);
			proxyWallet.updateWallet(wal);
			
			return new ResponseEntity<Object>(acc, HttpStatus.OK);
		}
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
}
