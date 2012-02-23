
package pl.com.bottega.ddd.infrastructure.sagas.impl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import pl.com.bottega.ddd.sagas.LoadSaga;
import pl.com.bottega.ddd.sagas.SagaAction;
import pl.com.bottega.ddd.sagas.SagaInstance;
import pl.com.bottega.ddd.sagas.SagaManager;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

@SuppressWarnings({"rawtypes", "unchecked"})
@Singleton
@Startup
public class CDISagaRegistry implements SagaRegistry {

    private Multimap<Class<?>, String> loadersInterestedIn = HashMultimap
            .create();

    private BeanManager beanManager;

    protected BeanManager getBeanManager() {
        if (this.beanManager == null) {
            try {
                InitialContext initialContext = new InitialContext();
                this.beanManager = (BeanManager) initialContext
                        .lookup("java:comp/BeanManager");
            } catch (NamingException e) {
                throw new IllegalStateException(
                        "unable to lookup bean manager", e);
            }
        }
        if (this.beanManager == null) {
            throw new IllegalStateException("unable to lookup bean manager");
        }
        return beanManager;
    }

    @Override
    public Collection<SagaManager<?,?>> getLoadersForEvent(Object event) {
        Collection<SagaManager<?,?>> results = new HashSet<SagaManager<?,?>>();
        BeanManager manager = getBeanManager();
        Collection<String> loadersBeansNames = loadersInterestedIn.get(event
                .getClass());
        for (String loaderBeanName : loadersBeansNames) {

            Set<Bean<?>> beanset = manager.getBeans(loaderBeanName);

            if (beanset.size() > 1) {
                throw new IllegalStateException("Should never occur: name "
                        + loaderBeanName
                        + " has more than one registered bean!");
            }

            Bean bean = beanset.iterator().next();
            CreationalContext<?> creationalContext = manager
                    .createCreationalContext(bean);

            Object reference = manager.getReference(bean, SagaManager.class,
                    creationalContext);
            if (reference == null) {
                throw new IllegalArgumentException(
                        "Cannot load saga manager for bean " + loaderBeanName);
            }
            SagaManager<?, ?> managerObject = (SagaManager<?, ?>)reference;
            results.add(managerObject);
        }
        return results;
    }

    @Override
    public SagaInstance<?> createSagaInstance(
            Class<? extends SagaInstance<?>> sagaType) {
        BeanManager manager = getBeanManager();
        Set<Bean<?>> beanset = manager.getBeans(sagaType);

        if (beanset.size() > 1) {
            throw new IllegalStateException("Should never occur: type "
                    + sagaType.getName()
                    + " has more than one registered bean!");
        }

        Bean bean = beanset.iterator().next();
        CreationalContext<?> creationalContext = beanManager
                .createCreationalContext(bean);

        Object reference = beanManager.getReference(bean, SagaInstance.class,
                creationalContext);
        if (reference == null) {
            throw new IllegalArgumentException(
                    "Cannot load saga instance for bean " + sagaType.getName());
        }
        return (SagaInstance) reference;
    }

    @PostConstruct
    public void refresh() {
        getBeanManager();
        loadersInterestedIn.clear();
        registerSagaLoaderBeans();
    }

    private void registerSagaLoaderBeans() {
        Set<Bean<?>> beanSet = beanManager.getBeans(SagaManager.class);

        for (Bean<?> bean : beanSet) {

            try {
                registerSagaLoader(bean.getBeanClass(), bean.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void registerSagaLoader(Class<?> loaderClass, String beanName) {
        for (Method method : loaderClass.getMethods()) {
            if (method.getAnnotation(SagaAction.class) != null
                    || method.getAnnotation(LoadSaga.class) != null) {
                Class<?>[] params = method.getParameterTypes();
                if (params.length == 1) {
                    loadersInterestedIn.put(params[0], beanName);
                } else {
                    throw new RuntimeException("incorrect event hadndler: "
                            + method);
                }
            }
        }
    }

}
