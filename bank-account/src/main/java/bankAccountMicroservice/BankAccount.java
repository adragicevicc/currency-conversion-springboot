package bankAccountMicroservice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

@Entity
public class BankAccount {

	@Id
	private long id;
	@Column(precision = 20, scale = 8)
	private BigDecimal usd;
	@Column(precision = 20, scale = 8)
	private BigDecimal eur;
	@Column(precision = 20, scale = 8)
	private BigDecimal rsd;
	@Column(precision = 20, scale = 8)
	private BigDecimal chf;
	@Column(precision = 20, scale = 8)
	private BigDecimal gbp;
	
	@Column(unique = true)
	private String email;

	public BankAccount() {
		super();
	}

	
	public BankAccount(long id, BigDecimal usd, BigDecimal eur, BigDecimal rsd, BigDecimal chf, BigDecimal gbp,
			String email) {
		super();
		this.id = id;
		this.usd = usd;
		this.eur = eur;
		this.rsd = rsd;
		this.chf = chf;
		this.gbp = gbp;
		this.email = email;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getUsd() {
		return usd;
	}

	public void setUsd(BigDecimal usd) {
		this.usd = usd;
	}
	
	public BigDecimal getEur() {
		return eur;
	}

	public void setEur(BigDecimal eur) {
		this.eur = eur;
	}

	public BigDecimal getRsd() {
		return rsd;
	}

	public void setRsd(BigDecimal rsd) {
		this.rsd = rsd;
	}

	public BigDecimal getChf() {
		return chf;
	}

	public void setChf(BigDecimal chf) {
		this.chf = chf;
	}

	public BigDecimal getGbp() {
		return gbp;
	}

	public void setGbp(BigDecimal gbp) {
		this.gbp = gbp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
}
