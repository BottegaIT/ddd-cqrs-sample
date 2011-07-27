package pl.com.bottega.erp.shipping.domain.events;

import pl.com.bottega.ddd.domain.DomainEvent;

public class ShipmentDeliveredEvent extends DomainEvent<Long> {

    public ShipmentDeliveredEvent(Long shipmentId) {
        super(shipmentId);
    }

    public Long getShipmentId() {
        return getAggregateId();
    }
}
