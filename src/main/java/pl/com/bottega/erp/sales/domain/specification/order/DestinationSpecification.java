/**
 * 
 */
package pl.com.bottega.erp.sales.domain.specification.order;

import java.util.Locale;

import pl.com.bottega.ddd.domain.annotations.DomainSpecification;
import pl.com.bottega.ddd.domain.sharedkernel.specification.CompositeSpecification;
import pl.com.bottega.erp.sales.domain.Order;

/**
 * Checks 
 * 
 * @author Slawek
 *
 */
@DomainSpecification
public class DestinationSpecification extends CompositeSpecification<Order>{	

	private Locale[] allowedLocale;
	
	public DestinationSpecification(Locale...allowedLocale) {	
		this.allowedLocale = allowedLocale;
	}


	/* (non-Javadoc)
	 * @see pl.com.bottega.ddd.domain.sharedcernel.specification.Specification#isSatisfiedBy(java.lang.Object)
	 */
	@Override
	public boolean isSatisfiedBy(Order order) {
		//TODO check if order destination is on allowedLocale
		return true;
	}


}
