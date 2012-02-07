/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * 
 * @author Slawek
 * 
 */
@Entity
@DomainEntity
public class OrderLine extends BaseEntity {

	@ManyToOne
	private Product product;

	private int quantity;

	/**
	 * before rebate
	 */
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "value", column = @Column(name = "regularCost_value")),
		@AttributeOverride(name = "currencyCode", column = @Column(name = "regularCost_currencyCode")) })
	private Money regularCost;

	/**
	 * after rebate
	 */
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "value", column = @Column(name = "effectiveCost_value")),
			@AttributeOverride(name = "currencyCode", column = @Column(name = "effectiveCost_currencyCode")) })
	private Money effectiveCost;

	/**
	 * For JPA only
	 */
	public OrderLine() {

	}

	public OrderLine(Product product, int quantity, RebatePolicy rebatePolicy) {
		this.product = product;
		this.quantity = quantity;
		recalculate(rebatePolicy);
	}

	public void increaseQuantity(int quantity, RebatePolicy rebatePolicy) {
		this.quantity += quantity;
		recalculate(rebatePolicy);
	}

	public void recalculate(RebatePolicy rebatePolicy) {
		regularCost = product.getPrice().multiplyBy(quantity);
		Money rebate = rebatePolicy.calculateRebate(product, quantity,
				regularCost);
		effectiveCost = regularCost.subtract(rebate);
	}

	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

	public Money getRegularCost() {
		return regularCost;
	}

	public Money getEffectiveCost() {
		return effectiveCost;
	}

}
