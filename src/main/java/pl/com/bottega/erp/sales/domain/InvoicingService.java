package pl.com.bottega.erp.sales.domain;

import javax.inject.Inject;

import pl.com.bottega.ddd.domain.annotations.DomainService;
import pl.com.bottega.ddd.domain.sharedkernel.Money;

/**
 * Sample Domain Service that contains logic that:
 * <ul>
 * <li> Does not have a natural place in any aggregate - we don't want to bloat Order with issuance of the Invoice
 * <li> Has broad dependencies - we don't want Order to become a 'God Class'
 * <li> Is used only (or not many) in one Use Case/user Story so is not essential for any Aggregate
 * <ul>
 * 
 * Notice that this Domain Service is managed by Container in order to be able to inject dependencies like Repo  
 * 
 * @author Slawek
 *
 */
@DomainService
public class InvoicingService {
	
	@Inject
	private ProductRepository productRepository;
	
	public Invoice issuance(Order order, TaxPolicy taxPolicy){
		//TODO refactor to InvoiceFactory
		Invoice invoice = new Invoice(order.getClient());
		
		for (OrderedProduct orderedProduct : order.getOrderedProducts()){
			Product product = productRepository.load(orderedProduct.getProductId());			
			
			Money net = orderedProduct.getEffectiveCost();			
			Tax tax = taxPolicy.calculateTax(product.getType(), net);			
						
			InvoiceLine invoiceLine = new InvoiceLine(product, orderedProduct.getQuantity(), net, tax);			
			invoice.addItem(invoiceLine);
		}
		
		return invoice;
	}
	
}
