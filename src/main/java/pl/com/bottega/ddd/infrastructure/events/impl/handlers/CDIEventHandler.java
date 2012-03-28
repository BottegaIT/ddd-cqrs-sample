package pl.com.bottega.ddd.infrastructure.events.impl.handlers;

import java.beans.Beans;
import java.lang.reflect.Method;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import pl.com.bottega.cqrs.command.handler.CommandHandler;

public class CDIEventHandler implements EventHandler {


    private final Class<?> eventType;
    private final String beanName;
    private final Method method;
    private final BeanManager beanManager;


    public CDIEventHandler(Class<?> eventType, String beanName, Method method, BeanManager beanManager) {
        this.eventType = eventType;
        this.beanName = beanName;
        this.method = method;
        this.beanManager = beanManager;
    }

    @Override
    public boolean canHandle(Object event) {
        return eventType.isAssignableFrom(event.getClass());
    }

    @Override
    public void handle(Object event) {
        Set<Bean<?>> beans = beanManager.getBeans(beanName);

        beans.iterator().next();
        // Following code could be externalized to some utility class
        // Consider creating CDIBeanProvider or something like this
        if (beans.size() > 1)
        {
            throw new IllegalStateException("Should never occur: name " + beanName + " has more than one registered CDI bean!");
        }

        Bean<?> bean = beans.iterator().next();
        CreationalContext<?> creationalContext = beanManager.createCreationalContext(bean);
        Object object = beanManager.getReference(bean, CommandHandler.class, creationalContext);


        try {
            method.invoke(object, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}