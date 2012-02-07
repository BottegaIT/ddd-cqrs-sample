package pl.com.bottega.erp.sales.domain;

import pl.com.bottega.ddd.domain.annotations.DomainPolicy;
import pl.com.bottega.ddd.domain.sharedkernel.Money;
import pl.com.bottega.erp.sales.domain.Product.ProductType;

/**
 * Sample Policy
 * 
 * @author Slawek
 *
 */
@DomainPolicy
public interface TaxPolicy {	

	/**
	 * calculates tax per product type based on net value
	 * @param productType
	 * @param net
	 * @return
	 */
	public Tax calculateTax(ProductType productType, Money net);

}
