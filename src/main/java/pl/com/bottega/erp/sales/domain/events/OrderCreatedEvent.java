package pl.com.bottega.erp.sales.domain.events;

import pl.com.bottega.ddd.domain.DomainEvent;

public class OrderCreatedEvent extends DomainEvent<Long> {

    public OrderCreatedEvent(Long orderId) {
        super(orderId);
    }

    public Long getOrderId() {
        return getAggregateId();
    }
}
