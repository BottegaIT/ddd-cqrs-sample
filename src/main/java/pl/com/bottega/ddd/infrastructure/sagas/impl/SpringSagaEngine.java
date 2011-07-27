package pl.com.bottega.ddd.infrastructure.sagas.impl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import pl.com.bottega.ddd.domain.DomainEvent;
import pl.com.bottega.ddd.infrastructure.events.impl.SimpleEventPublisher;
import pl.com.bottega.ddd.infrastructure.sagas.SagaEngine;
import pl.com.bottega.ddd.sagas.LoadSaga;
import pl.com.bottega.ddd.sagas.SagaAction;
import pl.com.bottega.ddd.sagas.SagaInstance;
import pl.com.bottega.ddd.sagas.SagaLoader;

@Component
public class SpringSagaEngine implements SagaEngine, ApplicationListener<ContextRefreshedEvent> {

    @Inject
    private ConfigurableListableBeanFactory beanFactory;

    @Inject
    private SimpleEventPublisher eventPublisher;

    // TODO convert to multimap
    private Map<Class<?>, Set<String>> loadersInterestedIn;

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void handleSagasEvent(DomainEvent<?> event) {
        Set<String> loadersBeansNames = loadersInterestedIn.get(event.getClass());
        for (String loaderBeanName : loadersBeansNames) {
            SagaLoader loader = beanFactory.getBean(loaderBeanName, SagaLoader.class);
            // TODO get saga type more reliably
            Class<? extends SagaInstance> sagaType = (Class<? extends SagaInstance>) ((ParameterizedType) loader
                    .getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
            Method handler = findHandlerMethod(loader.getClass(), event.getClass());
            try {
                Object sagaData = (Object) handler.invoke(loader, event);
                SagaInstance saga = beanFactory.getBean(sagaType);
                saga.setData(sagaData);
                Method eventHandler = findHandlerMethod(saga.getClass(), event.getClass());
                eventHandler.invoke(saga, event);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("rawtypes")
	private Method findHandlerMethod(Class<?> clas, Class<? extends DomainEvent> eventType) {
        for (Method method : clas.getMethods()) {
            if (method.getAnnotation(SagaAction.class) != null || method.getAnnotation(LoadSaga.class) != null) {
                if (method.getParameterTypes().length == 1 && method.getParameterTypes()[0].isAssignableFrom(eventType)) {
                    return method;
                }
            }
        }
        throw new RuntimeException("no method handling " + eventType);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (loadersInterestedIn == null) {
            loadersInterestedIn = new HashMap<Class<?>, Set<String>>();
            registerSagaBeans();
        }
    }

    private void registerSagaBeans() {
        String[] loadersNames = beanFactory.getBeanNamesForType(SagaLoader.class);
        for (String loaderBeanName : loadersNames) {
            BeanDefinition loaderBeanDefinition = beanFactory.getBeanDefinition(loaderBeanName);
            try {
                registerLoader(Class.forName(loaderBeanDefinition.getBeanClassName()), loaderBeanName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void registerLoader(Class<?> loaderClass, String beanName) {
        for (Method method : loaderClass.getMethods()) {
            if (method.getAnnotation(SagaAction.class) != null || method.getAnnotation(LoadSaga.class) != null) {
                Class<?>[] params = method.getParameterTypes();
                if (params.length == 1) {
                    registerInterestedIn(params[0], beanName);
                } else {
                    throw new RuntimeException("incorred event hadndler: " + method);
                }
            }

        }
    }

    private void registerInterestedIn(Class<?> eventType, String beanName) {
        Set<String> set = loadersInterestedIn.get(eventType);
        if (set == null) {
            set = new HashSet<String>();
            loadersInterestedIn.put(eventType, set);
        }
        set.add(beanName);
        eventPublisher.registerEventHandler(new SagaEventHandler(this, eventType));
    }
}
