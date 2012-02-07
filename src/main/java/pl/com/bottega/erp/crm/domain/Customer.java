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
package pl.com.bottega.erp.crm.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import pl.com.bottega.ddd.domain.BaseAggregateRoot;
import pl.com.bottega.ddd.domain.annotations.DomainAggregateRoot;
import pl.com.bottega.erp.crm.domain.events.CustomerStatusChangedEvent;

/**
 * @author Slawek
 *
 */
@Entity
@DomainAggregateRoot
public class Customer extends BaseAggregateRoot{

	public enum CustomerStatus{
		STANDARD, VIP, PLATINUM
	}
	
	@Enumerated(EnumType.STRING)
	private CustomerStatus status;
	
	
	public void changeStatus(CustomerStatus status){
		if (status.equals(this.status))
			return;
		
		this.status = status;
		
		//Sample Case: give 10% rebate for all draft orders - communication via events with different Bounded Context to achieve decoupling
		eventPublisher.publish(new CustomerStatusChangedEvent(getEntityId(), status));
	}
}
