package cryptoWalletMicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoWalletController {
	
	@Autowired
	private CryptoWalletRepository repo;
	
	@GetMapping("/crypto-wallet/user/{user}")
	public CryptoWallet getWallet(@PathVariable String user) {
		CryptoWallet temp = repo.findByEmail(user);
		
		if(temp == null) {
			throw new NullPointerException("Crpto wallet not found");
		}
		
		return temp;
	}
	
	@PutMapping("crypto-wallet/update")
	public CryptoWallet updateWallet(@RequestBody CryptoWallet cryptoWallet) {
		return repo.save(cryptoWallet);
	}
}
