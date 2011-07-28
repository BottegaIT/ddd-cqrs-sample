/**
 * 
 */
package pl.com.bottega.erp.sales.domain.events;

import pl.com.bottega.ddd.domain.DomainEvent;

/**
 * @author Slawek
 * 
 */
@SuppressWarnings("serial")
public class OrderSubmittedEvent implements DomainEvent {

    private final Long orderId;

    public OrderSubmittedEvent(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

}
