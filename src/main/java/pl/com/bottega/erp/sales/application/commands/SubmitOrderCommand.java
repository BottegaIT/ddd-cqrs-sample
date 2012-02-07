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
package pl.com.bottega.erp.sales.application.commands;

import java.io.Serializable;

import pl.com.bottega.cqrs.command.Command;

@SuppressWarnings("serial")
@Command(unique=true)
public class SubmitOrderCommand implements Serializable{

    private final Long orderId;

    public SubmitOrderCommand(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof SubmitOrderCommand) {
			SubmitOrderCommand command = (SubmitOrderCommand) obj;
			return orderId.equals(command.orderId);
		}
    	
    	return false;
    }

    @Override
    public int hashCode() {
    	return orderId.hashCode();
    }
}
