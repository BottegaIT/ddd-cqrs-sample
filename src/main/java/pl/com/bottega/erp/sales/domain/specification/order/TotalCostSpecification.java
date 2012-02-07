/**
 * 
 */
package pl.com.bottega.erp.sales.domain.specification.order;

import pl.com.bottega.ddd.domain.annotations.DomainSpecification;
import pl.com.bottega.ddd.domain.sharedkernel.Money;
import pl.com.bottega.ddd.domain.sharedkernel.specification.CompositeSpecification;
import pl.com.bottega.erp.sales.domain.Order;

/**
 * @author Slawek
 *
 */
@DomainSpecification
public class TotalCostSpecification extends CompositeSpecification<Order>{
	
	private Money min;
	
	private Money max;
	
	

	public TotalCostSpecification(Money min, Money max) {	
		this.min = min;
		this.max = max;
	}

	
	public TotalCostSpecification(Money max) {	
		this(null, max);
	}


	/* (non-Javadoc)
	 * @see pl.com.bottega.ddd.domain.sharedcernel.specification.Specification#isSatisfiedBy(java.lang.Object)
	 */
	@Override
	public boolean isSatisfiedBy(Order order) {
		if (min != null){
			if (order.getTotalCost().lessThan(min)){
				return false;
			}
		}
		
		if (max != null){
			if (order.getTotalCost().greaterThan(max)){
				return false;
			}
		}
		
		return true;
	}

}
