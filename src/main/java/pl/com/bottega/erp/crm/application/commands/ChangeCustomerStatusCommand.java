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
package pl.com.bottega.erp.crm.application.commands;

import java.io.Serializable;

import pl.com.bottega.cqrs.command.Command;
import pl.com.bottega.erp.crm.domain.Customer.CustomerStatus;

/**
 * @author Slawek
 *
 */
@SuppressWarnings("serial")
@Command
public class ChangeCustomerStatusCommand implements Serializable{

	private Long customerId;
	
	private CustomerStatus status;

	public ChangeCustomerStatusCommand(Long customerId, CustomerStatus status) {
		super();
		this.customerId = customerId;
		this.status = status;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public CustomerStatus getStatus() {
		return status;
	}
	
	
}
