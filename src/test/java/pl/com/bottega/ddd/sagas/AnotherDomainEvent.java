package pl.com.bottega.ddd.sagas;

import pl.com.bottega.ddd.domain.DomainEvent;

public class AnotherDomainEvent implements DomainEvent {
    private final Long aggregateId;
    private final String data;

    public AnotherDomainEvent(Long aggregateId, String data) {
        this.aggregateId = aggregateId;
        this.data = data;
    }

    public Long getAggregateId() {
        return aggregateId;
    }

    public String getData() {
        return data;
    }
}
