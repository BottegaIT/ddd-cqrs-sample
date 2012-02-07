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

    
    /** For JPA ONLY! */
    protected Shipment()
    {
    	
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
