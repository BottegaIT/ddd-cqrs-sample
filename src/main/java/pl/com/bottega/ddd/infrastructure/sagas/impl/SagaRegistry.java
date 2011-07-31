package pl.com.bottega.ddd.infrastructure.sagas.impl;

import java.util.Collection;

import pl.com.bottega.ddd.sagas.SagaInstance;
import pl.com.bottega.ddd.sagas.SagaLoader;

public interface SagaRegistry {

    Collection<SagaLoader> getLoadersForEvent(Object event);

    SagaInstance createSagaInstance(Class<? extends SagaInstance> sagaType);
}
