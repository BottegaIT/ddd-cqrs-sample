package pl.com.bottega.erp.sales.domain;

import java.io.Serializable;

import pl.com.bottega.ddd.domain.annotations.ValueObject;
import pl.com.bottega.ddd.domain.sharedkernel.Money;

/**
 * VO used to present ordered items and encapsulate Order internal impl
 * 
 * @author Slawek
 *
 */
@SuppressWarnings("serial")
@ValueObject
public class OrderedProduct implements Serializable{
	
	private Long productId;
	
	private String name;
	
	private int quantity;
	
	private Money effectiveCost;
	
	private Money regularCost;
	
	public OrderedProduct(Long productId, String name, int quantity, Money effectiveCost, Money regularCost) {
		this.productId = productId;
		this.name = name;
		this.quantity = quantity;
		this.effectiveCost = effectiveCost;
		this.regularCost = regularCost;
	}

	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}

	public Money getEffectiveCost() {
		return effectiveCost;
	}

	public Money getRegularCost() {
		return regularCost;
	}

	public Long getProductId() {
		return productId;
	}
	
	
}
