/**
 * 
 */
package pl.com.bottega.erp.sales.domain.specification.order;

import pl.com.bottega.ddd.domain.annotations.DomainSpecification;
import pl.com.bottega.ddd.domain.sharedkernel.specification.CompositeSpecification;
import pl.com.bottega.erp.sales.domain.Order;

/**
 * @author Slawek
 *
 */
@DomainSpecification
public class ItemsCountSpecification extends CompositeSpecification<Order>{
	
	private Integer min;
	
	private Integer max;
	
	

	public ItemsCountSpecification(Integer min, Integer max) {	
		this.min = min;
		this.max = max;
	}

	
	public ItemsCountSpecification(Integer max) {	
		this(null, max);
	}


	/* (non-Javadoc)
	 * @see pl.com.bottega.ddd.domain.sharedcernel.specification.Specification#isSatisfiedBy(java.lang.Object)
	 */
	@Override
	public boolean isSatisfiedBy(Order order) {
		if (min != null){
			if (order.getItemsNumber() < min){
				return false;
			}
		}
		
		if (max != null){
			if (order.getItemsNumber() > max){
				return false;
			}
		}
		
		return true;
	}

}
