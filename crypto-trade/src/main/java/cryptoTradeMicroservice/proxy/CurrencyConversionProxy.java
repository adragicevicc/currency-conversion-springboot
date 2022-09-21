package cryptoTradeMicroservice.proxy;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cryptoTradeMicroservice.dto.BankAccountDto;
import cryptoTradeMicroservice.dto.CurrencyConversionDto;

@FeignClient(name = "currency-conversion")
public interface CurrencyConversionProxy {
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}/user/{user}")
	BankAccountDto getConversionAccount(@PathVariable String from, @PathVariable String to, 
			@PathVariable BigDecimal quantity, @PathVariable String user);
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	CurrencyConversionDto getConversion(@PathVariable String from, @PathVariable String to, 
			@PathVariable BigDecimal quantity);

}
