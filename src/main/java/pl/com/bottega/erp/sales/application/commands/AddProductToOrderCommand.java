package pl.com.bottega.erp.sales.application.commands;

import java.io.Serializable;

import pl.com.bottega.cqrs.command.Command;

@SuppressWarnings("serial")
@Command
public class AddProductToOrderCommand implements Serializable{

	private Long orderId;
	
	private Long productId;
	
	private Integer quantity;
	
	

	public AddProductToOrderCommand(Long orderId, Long productId, Integer quantity) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public Long getOrderId() {
		return orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public Integer getQuantity() {
		return quantity;
	}
	
	
}
