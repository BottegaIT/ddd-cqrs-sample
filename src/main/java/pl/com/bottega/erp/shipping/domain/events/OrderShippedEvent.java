package pl.com.bottega.erp.shipping.domain.events;

import pl.com.bottega.ddd.domain.DomainEvent;

public class OrderShippedEvent extends DomainEvent<Long> {

    private Long shipmentId;

    public OrderShippedEvent(Long orderId, Long shipmentId) {
        super(orderId);
        this.shipmentId = shipmentId;
    }

    public Long getOrderId() {
        return getAggregateId();
    }

    public Long getShipmentId() {
        return shipmentId;
    }
}
