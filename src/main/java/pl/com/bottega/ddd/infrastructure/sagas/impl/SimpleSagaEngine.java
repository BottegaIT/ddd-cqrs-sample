package pl.com.bottega.ddd.infrastructure.sagas.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import pl.com.bottega.ddd.infrastructure.events.EventListener;
import pl.com.bottega.ddd.infrastructure.events.EventListeners;
import pl.com.bottega.ddd.infrastructure.sagas.SagaEngine;
import pl.com.bottega.ddd.sagas.LoadSaga;
import pl.com.bottega.ddd.sagas.SagaAction;
import pl.com.bottega.ddd.sagas.SagaInstance;
import pl.com.bottega.ddd.sagas.SagaManager;

/**
 * @author Rafał Jamróz
 */
@EventListeners
@Named
public class SimpleSagaEngine implements SagaEngine {

    private final SagaRegistry sagaRegistry;

    @Inject
    public SimpleSagaEngine(SagaRegistry sagaRegistry) {
        this.sagaRegistry = sagaRegistry;
    }

    @SuppressWarnings("rawtypes")
    @Override
    @EventListener
    public void handleSagasEvent(Object event) {
        Collection<SagaManager<?,?>> loaders = sagaRegistry.getLoadersForEvent(event);
        for (SagaManager loader : loaders) {
            SagaInstance sagaInstance = loadSaga(loader, event);
            invokeSagaActionForEvent(sagaInstance, event);
            if (sagaInstance.isCompleted()) {
                loader.removeSaga(sagaInstance);
            }
        }
    }

    private SagaInstance loadSaga(SagaManager loader, Object event) {
        Class<? extends SagaInstance<?>> sagaType = determineSagaTypeByLoader(loader);
        Object sagaData = loadSagaData(loader, event);
        if (sagaData == null) {
            sagaData = loader.createNewSagaData();
        }
        SagaInstance sagaInstance = sagaRegistry.createSagaInstance(sagaType);
        
        
        sagaInstance.setData(sagaData);
        return sagaInstance;
    }

    // TODO determine saga type more reliably
    private Class<? extends SagaInstance<?>> determineSagaTypeByLoader(SagaManager<?,?> loader) 
    {
    	Class<?> managerClass = loader.getClass();
    	Type firstInterface = managerClass.getGenericInterfaces()[0];
        Type type = ((ParameterizedType) firstInterface).getActualTypeArguments()[0];
        return (Class<? extends SagaInstance<?>>) type;
    }

    /**
     * TODO handle exception in more generic way
     */
    private Object loadSagaData(SagaManager loader, Object event) {
        Method loaderMethod = findHandlerMethodForEvent(loader.getClass(), event);
        try {
            Object sagaData = loaderMethod.invoke(loader, event);
            return sagaData;
        } catch (InvocationTargetException e) {
            // NRE is ok here, it means that saga hasn't been started yet
            if (e.getTargetException() instanceof NoResultException) {
                return null;
            } else {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void invokeSagaActionForEvent(SagaInstance<?> saga, Object event) {
        Method eventHandler = findHandlerMethodForEvent(saga.getClass(), event);
        try {
            eventHandler.invoke(saga, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Method findHandlerMethodForEvent(Class<?> type, Object event) {
        for (Method method : type.getMethods()) {
            if (method.getAnnotation(SagaAction.class) != null || method.getAnnotation(LoadSaga.class) != null) {
                if (method.getParameterTypes().length == 1
                        && method.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
                    return method;
                }
            }
        }
        throw new RuntimeException("no method handling " + event.getClass());
    }


}
