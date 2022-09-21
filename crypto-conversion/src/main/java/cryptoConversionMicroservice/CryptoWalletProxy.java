package cryptoConversionMicroservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "crypto-wallet")
public interface CryptoWalletProxy {
	@GetMapping("/crypto-wallet/user/{user}")
	CryptoWalletDto getWallet(@PathVariable String user);
	
	@PutMapping("/crypto-wallet/update")
	CryptoWalletDto updateWallet(@RequestBody CryptoWalletDto cryptoWallet);
}
