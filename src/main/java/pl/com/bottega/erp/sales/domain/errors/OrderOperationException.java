package pl.com.bottega.erp.sales.domain.errors;

@SuppressWarnings("serial")
public class OrderOperationException extends RuntimeException{

	private Long clientId;
	private Long orderId;
	
	public OrderOperationException(String message, Long clientId, Long orderId){
		super(message);
		this.clientId = clientId;
		this.orderId = orderId;
	}

	/**
	 * @param string
	 * @param id
	 */
	public OrderOperationException(String mesage, Long orderId) {
		this(mesage, null, orderId);
	}

	public Long getClientId() {
		return clientId;
	}

	public Long getOrderId() {
		return orderId;
	}
	
	
}
