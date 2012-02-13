/**
 * 
 */
package pl.com.bottega.erp.sales.domain.events;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import pl.com.bottega.ddd.domain.DomainEvent;

/**
 * @author Slawek
 * 
 */
@SuppressWarnings("serial")
public class OrderSubmittedEvent implements DomainEvent {

    private final Long orderId;

    public OrderSubmittedEvent(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
