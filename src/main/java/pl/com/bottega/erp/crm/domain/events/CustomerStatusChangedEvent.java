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
public class CustomerStatusChangedEvent implements DomainEvent {

    private final Long customerId;
    private final CustomerStatus status;

    public CustomerStatusChangedEvent(Long customerId, CustomerStatus status) {
        this.customerId = customerId;
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public CustomerStatus getStatus() {
        return status;
    }
}
