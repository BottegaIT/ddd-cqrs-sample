/**
 * 
 */
package pl.com.bottega.erp.sales.application.events;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import pl.com.bottega.ddd.application.annotation.ApplicationEvent;

import java.io.Serializable;

/**
 * 
 * @author Slawek
 * 
 */
@ApplicationEvent
@SuppressWarnings("serial")
public class ProductAddedToOrderEvent implements Serializable {

    private Long productid;

    private Long clientId;

    private int quantity;

    public ProductAddedToOrderEvent(Long productid, Long clientId, int quantity) {
        this.productid = productid;
        this.clientId = clientId;
        this.quantity = quantity;
    }

    public Long getProductid() {
        return productid;
    }

    public Long getClientId() {
        return clientId;
    }

    public int getQuantity() {
        return quantity;
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
