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
package pl.com.bottega.erp.shipping.application.events;

import javax.inject.Inject;

import pl.com.bottega.ddd.infrastructure.events.EventListener;
import pl.com.bottega.ddd.infrastructure.events.EventListeners;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;
import pl.com.bottega.erp.sales.presentation.ClientOrderDetailsDto;
import pl.com.bottega.erp.sales.presentation.OrderFinder;
import pl.com.bottega.erp.shipping.domain.Shipment;
import pl.com.bottega.erp.shipping.domain.ShipmentFactory;
import pl.com.bottega.erp.shipping.domain.ShipmentRepository;

/**
 * When an order is submitted by a customer automatically create a shipment in
 * WAITING status.
 * 
 * NOTICE: This is an example of communication across multiple bounded contexts
 * using events. In this context we can't access Order aggregate directly so we
 * use DTO from the read model instead.
 * 
 * @author Rafał Jamróz
 */
@EventListeners
public class OrderSubmittedForShippingListener {

    @Inject
    private ShipmentFactory factory;

    @Inject
    private OrderFinder orderFinder;

    @Inject
    private ShipmentRepository repository;

    @EventListener(asynchronous = true)
    public void handle(OrderSubmittedEvent event) {
        ClientOrderDetailsDto orderDetails = orderFinder.getClientOrderDetails(event.getOrderId());
        Shipment shipment = factory.createShipment(orderDetails);
        repository.persist(shipment);
    }
}
