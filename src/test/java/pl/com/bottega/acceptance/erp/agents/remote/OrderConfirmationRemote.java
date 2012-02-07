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
package pl.com.bottega.acceptance.erp.agents.remote;

import javax.inject.Inject;

import pl.com.bottega.acceptance.commons.RemoteAgent;
import pl.com.bottega.acceptance.erp.agents.OrderConfirmationAgent;
import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.erp.sales.application.commands.SubmitOrderCommand;
import pl.com.bottega.erp.sales.domain.Order.OrderStatus;
import pl.com.bottega.erp.sales.presentation.OrderFinder;

/**
 * @author Rafał Jamróz
 */
@RemoteAgent
public class OrderConfirmationRemote implements OrderConfirmationAgent {

    /**
     * Order in the scenario context.
     * 
     * @see ProductsListRemote#currentOrder
     */
    @Inject
    private CurrentOrder currentOrder;
    @Inject
    private Gate gate;
    @Inject
    private OrderFinder orderFinder;

    @Override
    public int getProductsCount() {
        return orderFinder.getClientOrderDetails(currentOrder.getOrderId()).getOrderedProducts().size();
    }

    @Override
    public void submit() {
        gate.dispatch(new SubmitOrderCommand(currentOrder.getOrderId()));
    }

    @Override
    public boolean isSubmitted() {
        OrderStatus orderStatus = orderFinder.getClientOrderDetails(currentOrder.getOrderId()).getOrderStatus();
        return OrderStatus.SUBMITTED.equals(orderStatus);
    }
}
