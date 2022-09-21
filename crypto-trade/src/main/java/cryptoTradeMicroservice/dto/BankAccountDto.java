package cryptoTradeMicroservice.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

public class BankAccountDto {

	private long id;
	private String email;
	private BigDecimal usd;
	private BigDecimal eur;
	private BigDecimal rsd;
	private BigDecimal chf;
	private BigDecimal gbp;
	
	public BankAccountDto() {
		super();
	}

	public BankAccountDto(long id, String email, BigDecimal usd, BigDecimal eur, BigDecimal rsd, BigDecimal chf,
			BigDecimal gbp) {
		super();
		this.id = id;
		this.email = email;
		this.usd = usd;
		this.eur = eur;
		this.rsd = rsd;
		this.chf = chf;
		this.gbp = gbp;
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
	
	
	
}
