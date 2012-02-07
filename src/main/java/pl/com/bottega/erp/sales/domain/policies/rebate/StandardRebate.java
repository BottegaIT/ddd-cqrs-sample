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
package pl.com.bottega.erp.sales.domain.policies.rebate;

import java.math.BigDecimal;

import pl.com.bottega.ddd.domain.annotations.DomainPolicyImpl;
import pl.com.bottega.ddd.domain.sharedkernel.Money;
import pl.com.bottega.erp.sales.domain.Product;
import pl.com.bottega.erp.sales.domain.RebatePolicy;

/**
 * Sample implementation of the Policy<br>
 * <br>
 * Calculate x% of the rebate if quantity of the product is greater than q
 * 
 * @author Slawek
 * 
 */
@DomainPolicyImpl
public class StandardRebate implements RebatePolicy {

	private BigDecimal rebateRatio;
	
	private int mininalQuantity;
	
	/**
	 * 
	 * @param rebate value of the rebate in % 
	 * @param mininalQuantity minimal quantity of the purchase that allows rebate
	 */
	public StandardRebate(double rebate, int mininalQuantity) {
		rebateRatio = new BigDecimal(rebate / 100);
		this.mininalQuantity = mininalQuantity;
	}

	@Override
	public Money calculateRebate(Product product, int quantity, Money regularCost){
		if (quantity >= mininalQuantity)
			return regularCost.multiplyBy(rebateRatio);
		return Money.ZERO;
	}

}
