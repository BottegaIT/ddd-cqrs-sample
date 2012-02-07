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
import pl.com.bottega.erp.sales.domain.Order;

/**
 * @author Slawek
 *
 */
@DomainSpecification
public class TotalCostSpecification extends CompositeSpecification<Order>{
	
	private Money min;
	
	private Money max;
	
	

	public TotalCostSpecification(Money min, Money max) {	
		this.min = min;
		this.max = max;
	}

	
	public TotalCostSpecification(Money max) {	
		this(null, max);
	}


	/* (non-Javadoc)
	 * @see pl.com.bottega.ddd.domain.sharedcernel.specification.Specification#isSatisfiedBy(java.lang.Object)
	 */
	@Override
	public boolean isSatisfiedBy(Order order) {
		if (min != null){
			if (order.getTotalCost().lessThan(min)){
				return false;
			}
		}
		
		if (max != null){
			if (order.getTotalCost().greaterThan(max)){
				return false;
			}
		}
		
		return true;
	}

}
