package pl.com.bottega.ddd.domain.support;

import javax.inject.Inject;
import javax.inject.Named;

import pl.com.bottega.ddd.domain.BaseAggregateRoot;
import pl.com.bottega.ddd.domain.DomainEventPublisher;

@Named
public class InjectorHelper {

    @Inject
    private DomainEventPublisher eventPublisher;

    public void injectDependencies(BaseAggregateRoot aggregateRoot) {
        if (aggregateRoot != null) {
            aggregateRoot.setEventPubslisher(eventPublisher);
        }
    }

}
