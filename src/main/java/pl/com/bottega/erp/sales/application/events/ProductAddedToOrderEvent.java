/**
 * 
 */
package pl.com.bottega.erp.sales.application.events;

import java.io.Serializable;

import pl.com.bottega.ddd.application.annotation.ApplicationEvent;

/**
 * 
 * @author Slawek
 *
 */
@ApplicationEvent
@SuppressWarnings("serial")
public class ProductAddedToOrderEvent implements Serializable{
	
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
}
