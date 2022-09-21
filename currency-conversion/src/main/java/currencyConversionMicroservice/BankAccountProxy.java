package currencyConversionMicroservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="bank-account")
public interface BankAccountProxy {

	@GetMapping("/bank-account/user/{user}")
	BankAccountDto getAccount(@PathVariable String user);

	@PutMapping("/bank-account/update")
	BankAccountDto updateAccount(@RequestBody BankAccountDto bankAccount);

}
