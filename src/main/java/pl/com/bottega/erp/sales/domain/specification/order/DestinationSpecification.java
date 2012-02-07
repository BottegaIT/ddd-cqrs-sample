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

import java.util.Locale;

import pl.com.bottega.ddd.domain.annotations.DomainSpecification;
import pl.com.bottega.ddd.domain.sharedkernel.specification.CompositeSpecification;
import pl.com.bottega.erp.sales.domain.Order;

/**
 * Checks 
 * 
 * @author Slawek
 *
 */
@DomainSpecification
public class DestinationSpecification extends CompositeSpecification<Order>{	

	private Locale[] allowedLocale;
	
	public DestinationSpecification(Locale...allowedLocale) {	
		this.allowedLocale = allowedLocale;
	}


	/* (non-Javadoc)
	 * @see pl.com.bottega.ddd.domain.sharedcernel.specification.Specification#isSatisfiedBy(java.lang.Object)
	 */
	@Override
	public boolean isSatisfiedBy(Order order) {
		//TODO check if order destination is on allowedLocale
		return true;
	}


}
