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
package pl.com.bottega.erp.shipping.presentation;

import java.io.Serializable;

import pl.com.bottega.erp.shipping.domain.ShippingStatus;

public class ShipmentDto implements Serializable {

    private Long shipmentId;
    private Long orderId;
    private ShippingStatus status;

    public ShipmentDto(Long shipmentId, Long orderId, ShippingStatus status) {
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.status = status;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public ShippingStatus getStatus() {
        return status;
    }

    public void setStatus(ShippingStatus status) {
        this.status = status;
    }
}
