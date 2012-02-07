package pl.com.bottega.erp.sales.domain.errors;

@SuppressWarnings("serial")
public class OrderCreationException extends RuntimeException{

	private Long clientId;
	
	public OrderCreationException(String message, Long clientId){
		super(message);
		this.clientId = clientId;
	}
	
	public Long getClientId(){
		return clientId;
	}
}
