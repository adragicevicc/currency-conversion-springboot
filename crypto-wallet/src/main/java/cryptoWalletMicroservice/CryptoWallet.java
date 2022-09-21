package cryptoWalletMicroservice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CryptoWallet {

	@Id
	private long id;
	@Column(precision = 20, scale = 8)
	private BigDecimal btc;
	@Column(precision = 20, scale = 8)
	private BigDecimal eth;
	@Column(precision = 20, scale = 8)
	private BigDecimal bnb;
	
	@Column (unique = true)
	private String email;

	public CryptoWallet() {
		super();
	}

	public CryptoWallet(long id, BigDecimal btc, BigDecimal eth, BigDecimal bnb, String email) {
		super();
		this.id = id;
		this.btc = btc;
		this.eth = eth;
		this.bnb = bnb;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
