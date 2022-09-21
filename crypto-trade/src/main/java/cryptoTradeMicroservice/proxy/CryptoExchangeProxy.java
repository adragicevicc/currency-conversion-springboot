package cryptoTradeMicroservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cryptoTradeMicroservice.CryptoTrade;

@FeignClient(name = "crypto-exchange")
public interface CryptoExchangeProxy {

	@GetMapping("/crypto-exchange/from/{from}/to/{to}")
	CryptoTrade getExchange(@PathVariable String from, @PathVariable String to);
}
