package bankAccountMicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {
	
	@Autowired
	private BankAccountRepository repo;
	
	@GetMapping("/bank-account/user/{user}")
	public BankAccount getAccount(@PathVariable String user) {
		BankAccount temp = repo.findByEmail(user);
		
		if(temp == null) {
			throw new NullPointerException("Bank account not found");
		}
		
		return temp;
	}
	
	@PutMapping("/bank-account/update")
	public BankAccount updateAccount(@RequestBody BankAccount bankAccount) {
		return repo.save(bankAccount);
	}
	
	
}
