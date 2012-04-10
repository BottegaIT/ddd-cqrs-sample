package pl.com.bottega.ddd.infrastructure.events.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.com.bottega.ddd.application.ApplicationEventPublisher;
import pl.com.bottega.ddd.domain.DomainEvent;
import pl.com.bottega.ddd.domain.DomainEventPublisher;
import pl.com.bottega.ddd.infrastructure.events.impl.handlers.EventHandler;

@Singleton
public class CDIEventPublisher implements ApplicationEventPublisher,
        DomainEventPublisher {


    private static final Logger LOGGER = LoggerFactory.getLogger(CDIEventPublisher.class);

    private Set<EventHandler> eventHandlers = new HashSet<EventHandler>();

    public void registerEventHandler(EventHandler handler) {
        eventHandlers.add(handler);
    }

    @Override
    public void publish(Serializable event) {
        doPublish(event);
    }

    @Override
    public void publish(DomainEvent event) {
        doPublish(event);
    }

    protected void doPublish(Object event) {
        for (EventHandler handler : new ArrayList<EventHandler>(eventHandlers)) {
            if (handler.canHandle(event)) {
                try {
                    handler.handle(event);
                } catch (Exception e) {
                    LOGGER.error("event handling error", e);
                }
            }
        }
    }

}
