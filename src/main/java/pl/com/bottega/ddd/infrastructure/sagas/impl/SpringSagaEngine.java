package pl.com.bottega.ddd.infrastructure.sagas.impl;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import pl.com.bottega.ddd.infrastructure.events.impl.SimpleEventPublisher;
import pl.com.bottega.ddd.infrastructure.events.impl.handlers.EventHandler;
import pl.com.bottega.ddd.infrastructure.sagas.SagaEngine;
import pl.com.bottega.ddd.sagas.LoadSaga;
import pl.com.bottega.ddd.sagas.SagaAction;
import pl.com.bottega.ddd.sagas.SagaInstance;

/**
 * @author Rafał Jamróz
 */
@Component
public class SpringSagaEngine implements SagaEngine {

    private final SagaRegistry sagaRegistry;

    private final SimpleEventPublisher eventPublisher;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    public SpringSagaEngine(SagaRegistry sagaRegistry, SimpleEventPublisher eventPublisher) {
        this.sagaRegistry = sagaRegistry;
        this.eventPublisher = eventPublisher;
    }

    @PostConstruct
    public void registerEventHandler() {
        eventPublisher.registerEventHandler(new SagaEventHandler(this));
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handleSagasEvent(Object event) {
        Collection<SagaInstance> sagas = sagaRegistry.loadSagasForEvent(event);
        for (SagaInstance saga : sagas) {
            invokeSagaActionForEvent(saga, event);
            if (saga.isCompleted()) {
                removeSagaInstance(saga);
            }
        }
    }

    private void invokeSagaActionForEvent(SagaInstance saga, Object event) {
        Method eventHandler = findHandlerMethodForEvent(saga, event);
        try {
            eventHandler.invoke(saga, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Method findHandlerMethodForEvent(SagaInstance saga, Object event) {
        for (Method method : saga.getClass().getMethods()) {
            if (method.getAnnotation(SagaAction.class) != null || method.getAnnotation(LoadSaga.class) != null) {
                if (method.getParameterTypes().length == 1
                        && method.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
                    return method;
                }
            }
        }
        throw new RuntimeException("no method handling " + event.getClass());
    }

    private void removeSagaInstance(SagaInstance<?> saga) {
        Object merged = entityManager.merge(saga.getData());
        entityManager.remove(merged);
    }

    private static class SagaEventHandler implements EventHandler {

        private final SagaEngine sagaEngine;

        public SagaEventHandler(SagaEngine sagaEngine) {
            this.sagaEngine = sagaEngine;
        }

        @Override
        public boolean canHandle(Object event) {
            return true;
        }

        @Override
        public void handle(Object event) {
            sagaEngine.handleSagasEvent(event);
        }
    }
}
