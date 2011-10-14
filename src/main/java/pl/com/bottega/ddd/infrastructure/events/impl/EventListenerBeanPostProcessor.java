package pl.com.bottega.ddd.infrastructure.events.impl;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import pl.com.bottega.ddd.infrastructure.events.EventListener;
import pl.com.bottega.ddd.infrastructure.events.impl.handlers.AsynchronousEventHandler;
import pl.com.bottega.ddd.infrastructure.events.impl.handlers.EventHandler;
import pl.com.bottega.ddd.infrastructure.events.impl.handlers.SpringEventHandler;
import pl.com.bottega.ddd.sagas.SagaInstance;

/**
 * Registers spring beans methods as event handlers in spring event publisher
 * (if needed).
 */
@Component
public class EventListenerBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    private BeanFactory beanFactory;
    private SimpleEventPublisher eventPublisher;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!(bean instanceof SagaInstance)) {
            for (Method method : bean.getClass().getMethods()) {
            	EventListener listenerAnnotation = method.getAnnotation(EventListener.class);            	
                
            	if (listenerAnnotation == null) {
                    continue;
                }
                
            	Class<?> eventType = method.getParameterTypes()[0];
                
                if (listenerAnnotation.asynchronous()){
                	//TODO just a temporary fake impl
                	EventHandler handler = new AsynchronousEventHandler(eventType, beanName, method, beanFactory);
                	//TODO add to some queue
                	eventPublisher.registerEventHandler(handler);                	
                }
                else{                
                	EventHandler handler = new SpringEventHandler(eventType, beanName, method, beanFactory);
                	eventPublisher.registerEventHandler(handler);
                }                                
            }
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // do nothing
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        eventPublisher = beanFactory.getBean(SimpleEventPublisher.class);
    }
}
