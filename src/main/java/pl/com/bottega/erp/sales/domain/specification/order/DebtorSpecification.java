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
package pl.com.bottega.erp.sales.domain.specification.order;

import pl.com.bottega.ddd.domain.annotations.DomainSpecification;
import pl.com.bottega.ddd.domain.sharedkernel.Money;
import pl.com.bottega.ddd.domain.sharedkernel.specification.CompositeSpecification;
import pl.com.bottega.erp.sales.domain.Client;
import pl.com.bottega.erp.sales.domain.Order;

/**
 * Checks 
 * 
 * @author Slawek
 *
 */
@DomainSpecification
public class DebtorSpecification extends CompositeSpecification<Order>{
	
	private Money maxDebt;
	
	

	public DebtorSpecification(Money maxDebt) {	
		this.maxDebt = maxDebt;
	}

	/**
	 * No debt is allowed
	 */
	public DebtorSpecification() {	
		this(Money.ZERO);
	}
	/* (non-Javadoc)
	 * @see pl.com.bottega.ddd.domain.sharedcernel.specification.Specification#isSatisfiedBy(java.lang.Object)
	 */
	@Override
	public boolean isSatisfiedBy(Order order) {
		Money debt = loadDebt(order.getClient());
		
		return debt.lessOrEquals(maxDebt);
	}


	/**
	 * @param client
	 * @return
	 */
	private Money loadDebt(Client client) {
		// TODO load debt using Repo/Service injected via constructor to this Spec
		return Money.ZERO;
	}

}
