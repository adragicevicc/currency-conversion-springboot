package currencyConversionMicroservice;

import java.io.Console;
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
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@Autowired 
	private BankAccountProxy proxyAccount;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<CurrencyConversion> response = new RestTemplate().getForEntity
				("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
	
		CurrencyConversion temp = response.getBody();
		
		return new CurrencyConversion(temp.getId(), from, to, temp.getConversionMultiple(), quantity, quantity.multiply(temp.getConversionMultiple()), temp.getEnvironment());
		
	}
	
	@RateLimiter(name = "default")
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}/user/{user}")
	public BankAccountDto getConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity, @PathVariable String user) throws Exception {
		
		CurrencyConversion exc = proxy.getExchange(from, to);
		BankAccountDto acc = proxyAccount.getAccount(user);
		
		CurrencyConversion currencyConversion = new CurrencyConversion(exc.getId(), from, to, exc.getConversionMultiple(), quantity, quantity.multiply(exc.getConversionMultiple()), exc.getEnvironment() + "feign");

		if(from.equals("USD") && acc.getUsd().compareTo(quantity) >=0) {
			acc.setUsd(acc.getUsd().subtract(quantity));
			

		} else if (from.equals("EUR") && acc.getEur().compareTo(quantity) >=0) {
			acc.setEur(acc.getEur().subtract(quantity));
		
		} else if (from.equals("RSD") && acc.getRsd().compareTo(quantity) >=0) {
			acc.setRsd(acc.getRsd().subtract(quantity));
		
		} else if (from.equals("CHF") && acc.getChf().compareTo(quantity) >=0) {
			acc.setChf(acc.getChf().subtract(quantity));
			
		} else if (from.equals("GBP") && acc.getGbp().compareTo(quantity) >=0) {
			acc.setGbp(acc.getGbp().subtract(quantity));
			
		} else {
			throw new RuntimeException("There is not enough money on your bank account.");

		}
		
		BigDecimal value = currencyConversion.getTotalConversionAmount();
		
		if(to.equals("USD")) {
			acc.setUsd(acc.getUsd().add(value));
			
		} else if (to.equals("EUR")) {
			acc.setEur(acc.getEur().add(value));
			
		} else if (to.equals("RSD")) {
			acc.setRsd(acc.getRsd().add(value));
			
		} else if (to.equals("CHF")) {
			acc.setChf(acc.getChf().add(value));
			
		} else if (to.equals("GBP")) {
			acc.setGbp(acc.getGbp().add(value));		
		} 
		
		
		proxyAccount.updateAccount(acc);
		
		return acc;

		
	}
}
