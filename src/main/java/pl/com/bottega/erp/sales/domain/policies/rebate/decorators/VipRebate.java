/**
 * 
 */
package pl.com.bottega.erp.sales.domain.policies.rebate.decorators;

import pl.com.bottega.ddd.domain.sharedkernel.Money;
import pl.com.bottega.erp.sales.domain.Product;
import pl.com.bottega.erp.sales.domain.RebatePolicy;

/**
 * Sample usage of the Decorator Design Pattern - allows to assemble additional logic <b>in the runtime<b><br>
 * <br> 
 * This example subtracts given value from total product cost if cost > threshold
 * 
 * @author Slawek
 *
 */
public class VipRebate extends RebateDecorator{
	
	private Money minimalThreshold;
	
	private Money rebateValue;

	public VipRebate(Money minimalThreshold, Money rebateValue) {
		this(null, minimalThreshold, rebateValue);
	}
	
	public VipRebate(RebatePolicy decorated, Money minimalThreshold, Money rebateValue) {
		super(decorated);	
		
		if (rebateValue.greaterThan(minimalThreshold))
			throw new IllegalArgumentException("Rabate can't be graterThan minimal threshold");
		
		this.minimalThreshold = minimalThreshold;
		this.rebateValue = rebateValue;
	}

	@Override
	public Money calculateRebate(Product product, int quantity,
			Money regularCost) {
		Money baseValue = (decorated == null) 
							? regularCost
							: decorated.calculateRebate(product, quantity, regularCost); 		
		
		if (baseValue.greaterThan(minimalThreshold))
			return baseValue.subtract(rebateValue);
		return baseValue;
	}

}
