/**
 * 
 */
package pl.com.bottega.ddd.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;


/**
 * @author Slawek
 * 
 */
@MappedSuperclass
public abstract class BaseAggregateRoot extends BaseEntity {

    /**
     * Sample of Domain Event usage<br>
     * Event Publisher is injected by Factory/Repo
     */
    @Transient
    protected DomainEventPublisher eventPublisher;

    /**
     * Sample technique of injecting Event Publisher into the Aggregate.<br>
     * <br>
     * Can be called only once by Factory/Repository<br>
     * Visible for package (Factory/Repository)
     */
    public void setEventPubslisher(DomainEventPublisher domainEventPubslisher) {
        if (this.eventPublisher != null)
            throw new IllegalStateException("Publisher is already set! Probably You have logical error in code");
        this.eventPublisher = domainEventPubslisher;
    }
}
