package pl.com.bottega.erp.sales.application.commands;

import pl.com.bottega.cqrs.command.Command;

@Command(unique=true)
public class SubmitOrderCommand {

    private final Long orderId;

    public SubmitOrderCommand(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof SubmitOrderCommand) {
			SubmitOrderCommand command = (SubmitOrderCommand) obj;
			return orderId.equals(command.orderId);
		}
    	
    	return false;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    	return orderId.hashCode();
    }
}
