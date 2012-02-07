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

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import pl.com.bottega.ddd.domain.annotations.ValueObject;
import pl.com.bottega.ddd.domain.sharedkernel.Money;

/**
 * 
 * @author Slawek
 *
 */
@Embeddable
@ValueObject
public class Tax {
	
	@Embedded
	private Money amount;

	private String description;
	
	/**
	 * For JPA only
	 */
	public Tax(){}
	
	public Tax(Money amount, String description) {
		super();
		this.amount = amount;
		this.description = description;
	}

	public Money getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}

	
}
