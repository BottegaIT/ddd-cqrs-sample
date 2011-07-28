package pl.com.bottega.ddd.infrastructure.sagas.impl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import pl.com.bottega.ddd.sagas.LoadSaga;
import pl.com.bottega.ddd.sagas.SagaAction;
import pl.com.bottega.ddd.sagas.SagaInstance;
import pl.com.bottega.ddd.sagas.SagaLoader;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * @author Rafał Jamróz
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Component
public class SpringSagaRegistry implements SagaRegistry, ApplicationListener<ContextRefreshedEvent> {

    private Multimap<Class<?>, String> loadersInterestedIn = HashMultimap.create();

    @Inject
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public Collection<SagaInstance> loadSagasForEvent(Object event) {
        Set<SagaInstance> results = new HashSet<SagaInstance>();
        Collection<String> loadersBeansNames = loadersInterestedIn.get(event.getClass());
        for (String loaderBeanName : loadersBeansNames) {
            SagaInstance saga = loadSaga(loaderBeanName, event);
            results.add(saga);
        }
        return results;
    }

    private SagaInstance loadSaga(String loaderBeanName, Object event) {
        SagaLoader loader = beanFactory.getBean(loaderBeanName, SagaLoader.class);
        Class<? extends SagaInstance> sagaType = determineSagaTypeOfLoader(loader);
        Object sagaData = loadSagaData(loader, event);
        SagaInstance saga = beanFactory.getBean(sagaType);
        saga.setData(sagaData);
        return saga;
    }

    // TODO determine saga type more reliably
    private Class<? extends SagaInstance> determineSagaTypeOfLoader(SagaLoader loader) {
        Type type = ((ParameterizedType) loader.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
        return (Class<? extends SagaInstance>) type;
    }

    private Object loadSagaData(SagaLoader loader, Object event) {
        Method loaderMethod = findLoaderMethodForEvent(loader, event);
        try {
            Object sagaData = loaderMethod.invoke(loader, event);
            return sagaData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO duplicate with {@link SpringSagaEngine#findHandlerMethod}
     */
    private Method findLoaderMethodForEvent(SagaLoader sagaLoader, Object event) {
        for (Method method : sagaLoader.getClass().getMethods()) {
            if (method.getAnnotation(LoadSaga.class) != null) {
                if (method.getParameterTypes().length == 1
                        && method.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
                    return method;
                }
            }
        }
        throw new RuntimeException("no method handling " + event.getClass());
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadersInterestedIn.clear();
        registerSagaBeans();
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
                    loadersInterestedIn.put(params[0], beanName);
                } else {
                    throw new RuntimeException("incorred event hadndler: " + method);
                }
            }
        }
    }
}
