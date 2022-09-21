package cryptoConversionMicroservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;


@RestController
public class CryptoConversionController {
	
	@Autowired
	private CryptoExchangeProxy proxy;
	
	@Autowired
	private CryptoWalletProxy proxyWallet;
	
	@GetMapping("/crypto-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CryptoConversion getConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		
		HashMap<String,String> uriVariables = new HashMap<>();
		uriVariables.put("from",from);
		uriVariables.put("to", to);
		
		ResponseEntity<CryptoConversion> response = new RestTemplate().getForEntity
				("http://localhost:8400/crypto-exchange/from/{from}/to/{to}", CryptoConversion.class, uriVariables);
		
		CryptoConversion temp = response.getBody();
		
		return new CryptoConversion(temp.getId(), from, to, temp.getConversionMultiple(), quantity, quantity.multiply(temp.getConversionMultiple()), temp.getEnvironment() + "feign");
	}
	
	@RateLimiter(name = "default")
	@GetMapping("/crypto-conversion-feign/from/{from}/to/{to}/quantity/{quantity}/user/{user}")
	public CryptoWalletDto getConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity, @PathVariable String user) throws Exception {
		
		
		CryptoConversion exc = proxy.getExchange(from, to);
		CryptoWalletDto wal = proxyWallet.getWallet(user);
		
		CryptoConversion cryptoConversion = new CryptoConversion(exc.getId(), from, to, exc.getConversionMultiple(), quantity, quantity.multiply(exc.getConversionMultiple()), exc.getEnvironment() + "feign");
		
		if(from.equals("BTC") && wal.getBtc().compareTo(quantity) >=0) {
			wal.setBtc(wal.getBtc().subtract(quantity));
			

		} else if (from.equals("ETH") && wal.getEth().compareTo(quantity) >=0) {
			wal.setEth(wal.getEth().subtract(quantity));
		
		} else if (from.equals("BNB") && wal.getBnb().compareTo(quantity) >=0) {
			wal.setBnb(wal.getBnb().subtract(quantity));
		
		} else {
			throw new Exception("There is not enough quantity on your crypto wallet.");
		}
		
		BigDecimal value = cryptoConversion.getTotalConversionAmount();
		
		if(to.equals("BTC")) {
			wal.setBtc(wal.getBtc().add(value));
			
		} else if (to.equals("ETH")) {
			wal.setEth(wal.getEth().add(value));
			
		} else if (to.equals("BNB")) {
			wal.setBnb(wal.getBnb().add(value));
		}
		
		proxyWallet.updateWallet(wal);
		
		return wal;
	}

}
