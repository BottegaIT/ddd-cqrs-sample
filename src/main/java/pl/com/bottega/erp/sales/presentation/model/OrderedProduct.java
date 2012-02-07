/**
 * 
 */
package pl.com.bottega.erp.sales.presentation.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Slawek
 *
 */
@Entity
public class OrderedProduct {
	
	@SuppressWarnings("unused")
	@Id
	@GeneratedValue
	private Long id;

	private Long productId;
	
	private Long clientId;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date eventDate;		
	
	private int quantity;

	public OrderedProduct(Long productId, Long clientId, int quantity, Date eventDate) {
		this.productId = productId;
		this.clientId = clientId;
		this.eventDate = eventDate;
		this.quantity = quantity;
	}


	public Long getProductId() {
		return productId;
	}


	public Long getClientId() {
		return clientId;
	}

	

	public Date getEventDate() {
		return eventDate;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	/** For JPA only. */
	protected OrderedProduct() {
		
	}
}
