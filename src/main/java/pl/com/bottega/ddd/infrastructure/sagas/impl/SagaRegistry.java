package pl.com.bottega.ddd.infrastructure.sagas.impl;

import java.util.Collection;

import pl.com.bottega.ddd.sagas.SagaInstance;
import pl.com.bottega.ddd.sagas.SagaManager;

public interface SagaRegistry {

    Collection<SagaManager> getLoadersForEvent(Object event);

    SagaInstance createSagaInstance(Class<? extends SagaInstance> sagaType);
}
