package pl.com.bottega.ddd.infrastructure.events.impl;

import pl.com.bottega.ddd.infrastructure.events.EventListener;
import pl.com.bottega.ddd.infrastructure.events.EventListeners;
import pl.com.bottega.ddd.infrastructure.events.impl.handlers.CDIEventHandler;
import pl.com.bottega.ddd.infrastructure.events.impl.handlers.EventHandler;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.reflect.Method;
import java.util.Set;

@Singleton
public class CDIEventsInitializer {

    @Inject
    private CDIEventPublisher publisher;

    BeanManager beanManager;

    @PostConstruct
    public void initialize() {
        beanManager = getBeanManager();

        Set<Bean<?>> allRegisteredBeans = beanManager.getBeans(Object.class);
        for (Bean<?> bean: allRegisteredBeans)
        {
            if (isEventListenerAnnotated(bean))
            {
                registerListenerInPublisher(bean);
            }
        }

    }

    private boolean isEventListenerAnnotated(Bean<?> bean)
    {
        return bean.getBeanClass().isAnnotationPresent(EventListeners.class);
    }

    private void registerListenerInPublisher(Bean<?> bean)
    {
        for (Method method : bean.getBeanClass().getMethods()) {
            EventListener listenerAnnotation = method.getAnnotation(EventListener.class);

            if (listenerAnnotation == null) {
                continue;
            }

            Class<?> eventType = method.getParameterTypes()[0];

            if (listenerAnnotation.asynchronous()){
                // TODO
            }
            else{
                EventHandler handler = new CDIEventHandler(eventType, bean.getName(), method, getBeanManager());
                publisher.registerEventHandler(handler);
            }
        }


    }

    private BeanManager getBeanManager() {
        if (this.beanManager == null)
        {
            try {
                InitialContext initialContext = new InitialContext();
                beanManager = (BeanManager) initialContext
                        .lookup("java:comp/BeanManager");
            } catch (NamingException e) {
                throw new IllegalStateException("unable to lookup bean manager", e);
            }
        }
        return beanManager;
    }

}
