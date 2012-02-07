package pl.com.bottega.ddd.domain;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
