package pl.com.bottega.erp.sales.application.commands.handlers;

import java.util.Locale;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.cqrs.command.handler.CommandHandlerAnnotation;
import pl.com.bottega.ddd.application.SystemUser;
import pl.com.bottega.ddd.domain.sharedcernel.Money;
import pl.com.bottega.ddd.domain.sharedcernel.specification.ConjunctionSpecification;
import pl.com.bottega.ddd.domain.sharedcernel.specification.Specification;
import pl.com.bottega.erp.sales.application.commands.SubmitOrderCommand;
import pl.com.bottega.erp.sales.domain.Invoice;
import pl.com.bottega.erp.sales.domain.InvoiceRepository;
import pl.com.bottega.erp.sales.domain.InvoicingService;
import pl.com.bottega.erp.sales.domain.Order;
import pl.com.bottega.erp.sales.domain.OrderRepository;
import pl.com.bottega.erp.sales.domain.TaxPolicy;
import pl.com.bottega.erp.sales.domain.errors.OrderOperationException;
import pl.com.bottega.erp.sales.domain.policies.tax.DefaultTaxPolicy;
import pl.com.bottega.erp.sales.domain.specification.order.DebtorSpecification;
import pl.com.bottega.erp.sales.domain.specification.order.DestinationSpecification;
import pl.com.bottega.erp.sales.domain.specification.order.ItemsCountSpecification;
import pl.com.bottega.erp.sales.domain.specification.order.RestrictedProductsSpecification;
import pl.com.bottega.erp.sales.domain.specification.order.TotalCostSpecification;

@Transactional
@CommandHandlerAnnotation
public class SubmitOrderCommandHandler implements CommandHandler<SubmitOrderCommand, Void> {

    @Inject
    private OrderRepository orderRepository;
    
    @Inject
    private InvoiceRepository invoiceRepository;
	
    @Inject
	private InvoicingService invoicingService;
	
    @Inject
	private SystemUser systemUser;

    @Override
    @Transactional
    public Void handle(SubmitOrderCommand command) {
        Order order = orderRepository.load(command.getOrderId());
        
        Specification<Order> orderSpecification = generateSpecification(systemUser);
		if (! orderSpecification.isSatisfiedBy(order))
			throw new OrderOperationException("Order does not meet specification", order.getId());            
                		
		//Domain logic
		order.submit();		
		//Domain service
		Invoice invoice = invoicingService.issuance(order, generateTaxPolicy(systemUser));
                
        orderRepository.save(order);
        invoiceRepository.save(invoice);
        
        return null;
    }

    /**
	 * Assembling Spec contains Business Logic, therefore it may be moved to domain Building Block - OrderSpecificationFactory 
	 * 
	 * @param systemUser
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Specification<Order> generateSpecification(SystemUser systemUser) {
		Specification<Order> specification = new ConjunctionSpecification<Order>( 
													new DestinationSpecification(Locale.CHINA),//.not(), // - do not send to China
													new ItemsCountSpecification(100),//max 100 items
													  	new DebtorSpecification()//not debts or max 1000  => debtors can buy for max 1000
															.or(new TotalCostSpecification(new Money(1000.0))));
		
		//vip can buy some nice stuff
		if (! isVip(systemUser)){
			
			specification = specification.and(new RestrictedProductsSpecification());
		}
		
		return specification;
	}



	/**
	 * @param systemUser
	 * @return
	 */
	private boolean isVip(SystemUser systemUser) {
		// TODO Auto-generated method stub
		return false;
	}



	/**
	 * @param systemUser
	 * @return
	 */
	private TaxPolicy generateTaxPolicy(SystemUser systemUser) {
		// TODO determine tax based on user's location
		return new DefaultTaxPolicy();
	}
}
