/**
 * 
 */
package pl.com.bottega.erp.crm.domain.events;

import pl.com.bottega.ddd.domain.DomainEvent;
import pl.com.bottega.erp.crm.domain.Customer.CustomerStatus;

/**
 * @author Slawek
 * 
 */
@SuppressWarnings("serial")
public class CustomerStatusChangedEvent extends DomainEvent<Long> {

    private CustomerStatus status;

    public CustomerStatusChangedEvent(Long customerId, CustomerStatus status) {
        super(customerId);
        this.status = status;
    }

    public Long getCustomerId() {
        return getAggregateId();
    }

    public CustomerStatus getStatus() {
        return status;
    }
}
