package cryptoTradeMicroservice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class CryptoTrade {
	
	@Id
	private long id;
	@Column(name = "trade_from")
	private String from;
	@Column(name = "trade_to")
	private String to;
	@Transient
	private BigDecimal quantity;
	@Column(name = "conversion_multiple", precision = 20, scale = 8)
	private BigDecimal conversionMultiple;
	
	@Transient
	private String environment;

	public CryptoTrade() {
		super();
	}

	public CryptoTrade(long id, String from, String to, BigDecimal quantiy, BigDecimal conversionMultiple, String environment) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.quantity = quantiy;
		this.conversionMultiple = conversionMultiple;
		this.environment = environment;
	}
	
	public CryptoTrade(long id, String from, String to, BigDecimal conversionMultiple, String environment) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionMultiple = conversionMultiple;
		this.environment = environment;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
	public BigDecimal getQuantity() {
		return quantity;
	}
	
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
	}

	public void setConversionMultiple(BigDecimal conversionMultiple) {
		this.conversionMultiple = conversionMultiple;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	

}
