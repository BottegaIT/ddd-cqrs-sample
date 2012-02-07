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
package pl.com.bottega.erp.sales.application.listeners;

import pl.com.bottega.ddd.infrastructure.events.EventListener;
import pl.com.bottega.ddd.infrastructure.events.EventListeners;
import pl.com.bottega.erp.crm.domain.Customer.CustomerStatus;
import pl.com.bottega.erp.crm.domain.events.CustomerStatusChangedEvent;

/**
 * Sample listener that communicates with other Bounded Context
 * 
 * @author Slawek
 *
 */
@EventListeners
public class CustomerStatusChangedListener{

	@EventListener(asynchronous=true)	
	public void handle(CustomerStatusChangedEvent event) {
		if (event.getStatus() == CustomerStatus.VIP){
			calculateReabteForAllDraftOrders(event.getCustomerId(), 10);
		}		
	}

	/**
	 * @param customerId
	 * @param i
	 */
	private void calculateReabteForAllDraftOrders(Long customerId, int i) {
		// TODO Auto-generated method stub
		
	}

}
