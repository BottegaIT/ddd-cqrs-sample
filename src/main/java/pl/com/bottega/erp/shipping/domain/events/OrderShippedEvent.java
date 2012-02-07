package pl.com.bottega.erp.shipping.domain.events;

import pl.com.bottega.ddd.domain.DomainEvent;

public class OrderShippedEvent implements DomainEvent {

    private final Long orderId;
    private final Long shipmentId;

    public OrderShippedEvent(Long orderId, Long shipmentId) {
        this.orderId = orderId;
        this.shipmentId = shipmentId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getShipmentId() {
        return shipmentId;
    }
}
