package pl.com.bottega.ddd.infrastructure.sagas.impl;

import java.util.Collection;

import pl.com.bottega.ddd.sagas.SagaInstance;

public interface SagaRegistry {

    Collection<SagaInstance> loadSagasForEvent(Object event);
}
