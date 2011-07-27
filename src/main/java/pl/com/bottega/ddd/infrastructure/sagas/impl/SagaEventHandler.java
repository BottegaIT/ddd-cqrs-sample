package pl.com.bottega.ddd.infrastructure.sagas.impl;

import pl.com.bottega.ddd.domain.DomainEvent;
import pl.com.bottega.ddd.infrastructure.events.impl.handlers.EventHandler;
import pl.com.bottega.ddd.infrastructure.sagas.SagaEngine;

public class SagaEventHandler implements EventHandler {

    private final Class<?> eventType;
    private final SagaEngine sagaEngine;

    public SagaEventHandler(SagaEngine sagaEngine, Class<?> eventType) {
        this.sagaEngine = sagaEngine;
        this.eventType = eventType;
    }

    @Override
    public boolean canHandle(Object event) {
        return eventType.isInstance(event);
    }

    @Override
    public void handle(Object event) {
        sagaEngine.handleSagasEvent((DomainEvent<?>) event);
    }
}
