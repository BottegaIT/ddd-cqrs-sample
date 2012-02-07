/**
 * 
 */
package pl.com.bottega.erp.crm.application.commands;

import java.io.Serializable;

import pl.com.bottega.cqrs.command.Command;
import pl.com.bottega.erp.crm.domain.Customer.CustomerStatus;

/**
 * @author Slawek
 *
 */
@SuppressWarnings("serial")
@Command
public class ChangeCustomerStatusCommand implements Serializable{

	private Long customerId;
	
	private CustomerStatus status;

	public ChangeCustomerStatusCommand(Long customerId, CustomerStatus status) {
		super();
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
