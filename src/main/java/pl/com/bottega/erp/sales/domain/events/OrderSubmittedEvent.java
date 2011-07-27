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
public class OrderSubmittedEvent extends DomainEvent<Long> {

    public OrderSubmittedEvent(Long orderId) {
        super(orderId);
    }

    public Long getOrderId() {
        return getAggregateId();
    }

}
