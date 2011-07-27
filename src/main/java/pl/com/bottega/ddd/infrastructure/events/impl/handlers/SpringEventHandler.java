package pl.com.bottega.ddd.infrastructure.events.impl.handlers;

import java.lang.reflect.Method;

import org.springframework.beans.factory.BeanFactory;



public class SpringEventHandler implements EventHandler {

    private final Class<?> eventType;
    private final String beanName;
    private final Method method;
    private final BeanFactory beanFactory;

    public SpringEventHandler(Class<?> eventType, String beanName, Method method, BeanFactory beanFactory) {
        this.eventType = eventType;
        this.beanName = beanName;
        this.method = method;
        this.beanFactory = beanFactory;
    }

    public boolean canHandle(Object event) {
        return eventType.isAssignableFrom(event.getClass());
    }

    @Override
    public void handle(Object event) {
        Object bean = beanFactory.getBean(beanName);
        try {
            method.invoke(bean, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
