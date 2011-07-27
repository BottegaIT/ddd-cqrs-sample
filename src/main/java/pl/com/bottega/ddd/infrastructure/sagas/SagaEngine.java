package pl.com.bottega.ddd.infrastructure.sagas;

import pl.com.bottega.ddd.domain.DomainEvent;

public interface SagaEngine {

    void handleSagasEvent(DomainEvent<?> event);
}
