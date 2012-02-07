package pl.com.bottega.erp.shipping.domain.events;

import pl.com.bottega.ddd.domain.DomainEvent;

public class ShipmentDeliveredEvent implements DomainEvent {

    private final Long shipmentId;

    public ShipmentDeliveredEvent(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getShipmentId() {
        return shipmentId;
    }
}
