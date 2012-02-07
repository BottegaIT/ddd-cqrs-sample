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
