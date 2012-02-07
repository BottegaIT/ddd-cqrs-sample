/**
 * 
 */
package pl.com.bottega.erp.sales.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import pl.com.bottega.ddd.domain.BaseEntity;
import pl.com.bottega.ddd.domain.annotations.DomainEntity;
import pl.com.bottega.ddd.domain.sharedkernel.Money;

/**
 * @author Slawek
 *
 */
@Entity
@DomainEntity
public class InvoiceLine extends BaseEntity{
		
	@ManyToOne
	private Product product;
	
	private int quantity;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "value", column = @Column(name = "net_value")),
		@AttributeOverride(name = "currencyCode", column = @Column(name = "net_currencyCode")) })
	private Money net;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "value", column = @Column(name = "gros_value")),
		@AttributeOverride(name = "currencyCode", column = @Column(name = "gros_currencyCode")) })
	private Money gros;
	
	@Embedded
	private Tax tax;

	/**
	 * JPA only
	 */
	public InvoiceLine(){}
	

	InvoiceLine(Product product, int quantity, Money net, Tax tax) {
		this.product = product;
		this.quantity = quantity;
		this.net = net;
		this.tax = tax;
		
		this.gros = net.add(tax.getAmount());	
	}

	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

	public Money getNet() {
		return net;
	}

	public Money getGros() {
		return gros;
	}
	
	public Tax getTax(){
		return tax;
	}
}
