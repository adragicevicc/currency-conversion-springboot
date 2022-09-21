package cryptoTradeMicroservice.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

public class CryptoWalletDto {

	private long id;
	private String email;
	private BigDecimal btc;
	private BigDecimal eth;
	private BigDecimal bnb;
	
	public CryptoWalletDto() {
		super();
	}

	public CryptoWalletDto(long id, String email, BigDecimal btc, BigDecimal eth, BigDecimal bnb) {
		super();
		this.id = id;
		this.email = email;
		this.btc = btc;
		this.eth = eth;
		this.bnb = bnb;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getBtc() {
		return btc;
	}

	public void setBtc(BigDecimal btc) {
		this.btc = btc;
	}

	public BigDecimal getEth() {
		return eth;
	}

	public void setEth(BigDecimal eth) {
		this.eth = eth;
	}

	public BigDecimal getBnb() {
		return bnb;
	}

	public void setBnb(BigDecimal bnb) {
		this.bnb = bnb;
	}
	
	
}
