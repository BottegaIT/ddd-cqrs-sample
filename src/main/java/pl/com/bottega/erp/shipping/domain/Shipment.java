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
package pl.com.bottega.erp.shipping.domain;

import javax.persistence.Entity;

import pl.com.bottega.ddd.domain.BaseAggregateRoot;
import pl.com.bottega.ddd.domain.annotations.DomainAggregateRoot;
import pl.com.bottega.erp.shipping.domain.events.OrderShippedEvent;
import pl.com.bottega.erp.shipping.domain.events.ShipmentDeliveredEvent;

/**
 * @author Rafał Jamróz
 */
@Entity
@DomainAggregateRoot
public class Shipment extends BaseAggregateRoot {

    private Long orderId;

    private ShippingStatus status;

    private Shipment() {
    }

    public Shipment(Long orderId) {
        this.orderId = orderId;
        status = ShippingStatus.WAITING;
    }

    /**
     * Shipment has been sent to the customer.
     */
    public void ship() {
        if (status != ShippingStatus.WAITING) {
            throw new IllegalStateException("cannot ship in status " + status);
        }
        status = ShippingStatus.SENT;
        eventPublisher.publish(new OrderShippedEvent(orderId, getEntityId()));
    }

    /**
     * Shipment has been confirmed received by the customer.
     */
    public void deliver() {
        if (status != ShippingStatus.SENT) {
            throw new IllegalStateException("cannot deliver in status " + status);
        }
        status = ShippingStatus.DELIVERED;
        eventPublisher.publish(new ShipmentDeliveredEvent(getEntityId()));
    }

    public Long getOrderId() {
        return orderId;
    }

}
