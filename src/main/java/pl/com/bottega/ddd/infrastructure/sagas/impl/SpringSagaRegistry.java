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
import pl.com.bottega.ddd.sagas.SagaManager;

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
    public Collection<SagaManager> getLoadersForEvent(Object event) {
        Collection<SagaManager> results = new HashSet<SagaManager>();
        Collection<String> loadersBeansNames = loadersInterestedIn.get(event.getClass());
        for (String loaderBeanName : loadersBeansNames) {
            SagaManager loader = beanFactory.getBean(loaderBeanName, SagaManager.class);
            results.add(loader);
        }
        return results;
    }

    @Override
    public SagaInstance createSagaInstance(Class<? extends SagaInstance> sagaType) {
        return (SagaInstance) beanFactory.getBean(sagaType);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadersInterestedIn.clear();
        registerSagaLoaderBeans();
    }

    private void registerSagaLoaderBeans() {
        String[] loadersNames = beanFactory.getBeanNamesForType(SagaManager.class);
        for (String loaderBeanName : loadersNames) {
            BeanDefinition loaderBeanDefinition = beanFactory.getBeanDefinition(loaderBeanName);
            try {
                registerSagaLoader(Class.forName(loaderBeanDefinition.getBeanClassName()), loaderBeanName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void registerSagaLoader(Class<?> loaderClass, String beanName) {
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
