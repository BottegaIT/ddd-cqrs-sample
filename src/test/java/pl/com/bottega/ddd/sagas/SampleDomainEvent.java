package pl.com.bottega.ddd.sagas;

import pl.com.bottega.ddd.domain.DomainEvent;

public class SampleDomainEvent implements DomainEvent {

    private final Long aggregateId;

    public SampleDomainEvent(Long aggregateId) {
        this.aggregateId = aggregateId;
    }

    public Long getAggregateId() {
        return aggregateId;
    }
}
