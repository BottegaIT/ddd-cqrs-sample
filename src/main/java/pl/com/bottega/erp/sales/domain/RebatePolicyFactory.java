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

import javax.inject.Inject;

import pl.com.bottega.ddd.application.SystemUser;
import pl.com.bottega.ddd.domain.annotations.DomainFactory;
import pl.com.bottega.ddd.domain.sharedkernel.Money;
import pl.com.bottega.erp.sales.domain.policies.rebate.StandardRebate;
import pl.com.bottega.erp.sales.domain.policies.rebate.decorators.VipRebate;

/**
 * @author Slawek
 *
 */
@DomainFactory
public class RebatePolicyFactory {

	@Inject
	private SystemUser systemUser;
	
	public RebatePolicy createRebatePolicy() {		
		RebatePolicy rebatePolicy = new StandardRebate(10, 1);
		
		if (isVip(systemUser)){
			rebatePolicy = new VipRebate(rebatePolicy, new Money(1000.0), new Money(100));
		}
		
		return rebatePolicy;
	}
	
	/**
	 * @param systemUser
	 * @return
	 */
	private boolean isVip(SystemUser systemUser) {
		// TODO Auto-generated method stub
		return true;
	}
}
