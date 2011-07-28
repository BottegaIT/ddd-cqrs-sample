package pl.com.bottega.erp.sales.domain.events;

import pl.com.bottega.ddd.domain.DomainEvent;

public class OrderCreatedEvent implements DomainEvent {

    private final Long orderId;

    public OrderCreatedEvent(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
}
