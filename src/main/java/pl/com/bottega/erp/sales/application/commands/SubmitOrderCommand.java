package pl.com.bottega.erp.sales.application.commands;

import java.io.Serializable;

import pl.com.bottega.cqrs.command.Command;

@SuppressWarnings("serial")
@Command(unique=true)
public class SubmitOrderCommand implements Serializable{

    private final Long orderId;

    public SubmitOrderCommand(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof SubmitOrderCommand) {
			SubmitOrderCommand command = (SubmitOrderCommand) obj;
			return orderId.equals(command.orderId);
		}
    	
    	return false;
    }

    @Override
    public int hashCode() {
    	return orderId.hashCode();
    }
}
