/**
 * 
 */
package pl.com.bottega.erp.crm.application.commands.handlers;

import javax.inject.Inject;

import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.cqrs.command.handler.CommandHandlerAnnotation;
import pl.com.bottega.erp.crm.application.commands.ChangeCustomerStatusCommand;
import pl.com.bottega.erp.crm.domain.Customer;
import pl.com.bottega.erp.crm.domain.CustomerRepository;

/**
 * @author Slawek
 *
 */
@CommandHandlerAnnotation
public class ChangeCustomerStatusCommandHandler implements CommandHandler<ChangeCustomerStatusCommand, Void>{

	@Inject
	private CustomerRepository customerRepository; 
	
	@Override
	public Void handle(ChangeCustomerStatusCommand command) {
		Customer customer = customerRepository.load(command.getCustomerId());
		customer.changeStatus(command.getStatus());
		customerRepository.save(customer);		
		return null;
	}

}
