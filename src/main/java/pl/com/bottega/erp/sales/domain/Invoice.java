package pl.com.bottega.erp.sales.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import pl.com.bottega.ddd.domain.BaseAggregateRoot;
import pl.com.bottega.ddd.domain.annotations.DomainAggregateRoot;
import pl.com.bottega.ddd.domain.sharedkernel.Money;

/**
 * 
 * @author Slawek
 * 
 */
@DomainAggregateRoot
@Entity
public class Invoice extends BaseAggregateRoot {

	@ManyToOne
	private Client client;

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

	@OneToMany(cascade = CascadeType.ALL)
	private List<InvoiceLine> items;

	public Invoice(Client client) {
		this.client = client;
		items = new ArrayList<InvoiceLine>();
		
		net = Money.ZERO;
		gros = Money.ZERO;
	}
	
	/**
	 * For JPA Only
	 */
	public Invoice(){}

	public void addItem(InvoiceLine item) {
		items.add(item);

		net = net.add(item.getNet());
		gros = gros.add(item.getGros());
	}

	/**
	 * 
	 * @return immutable projection
	 */
	public List<InvoiceLine> getItems() {
		return Collections.unmodifiableList(items);
	}

	public Client getClient() {
		return client;
	}

	public Money getNet() {
		return net;
	}

	public Money getGros() {
		return gros;
	}

}
