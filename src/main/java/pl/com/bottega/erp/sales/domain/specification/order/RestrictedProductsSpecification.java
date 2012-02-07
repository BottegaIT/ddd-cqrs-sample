/**
 * 
 */
package pl.com.bottega.erp.sales.domain.specification.order;

import pl.com.bottega.ddd.domain.annotations.DomainSpecification;
import pl.com.bottega.ddd.domain.sharedkernel.specification.CompositeSpecification;
import pl.com.bottega.erp.sales.domain.Order;
import pl.com.bottega.erp.sales.domain.OrderedProduct;
import pl.com.bottega.erp.sales.domain.Product;

/**
 * Checks 
 * 
 * @author Slawek
 *
 */
@DomainSpecification
public class RestrictedProductsSpecification extends CompositeSpecification<Order>{	

	private Product[] restrictedProducts;
	
	public RestrictedProductsSpecification(Product... restrictedProducts) {	
		this.restrictedProducts = restrictedProducts;
	}


	/* (non-Javadoc)
	 * @see pl.com.bottega.ddd.domain.sharedcernel.specification.Specification#isSatisfiedBy(java.lang.Object)
	 */
	@Override
	public boolean isSatisfiedBy(Order order) {
		for (OrderedProduct product : order.getOrderedProducts()){
			if(isWeaponOfMassDestruction(product))
				return false;
		}
		
		return true;
	}


	/**
	 * @param product 
	 * @return
	 */
	private boolean isWeaponOfMassDestruction(OrderedProduct product) {
		// TODO Auto-generated method stub
		return false;
	}


}
