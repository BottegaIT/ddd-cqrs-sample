/**
 * 
 */
package pl.com.bottega.erp.sales.domain.policies.rebate.decorators;

import pl.com.bottega.erp.sales.domain.RebatePolicy;

/**
 * @author Slawek
 *
 */
public abstract class RebateDecorator implements RebatePolicy{
	
	protected RebatePolicy decorated; 

	protected RebateDecorator(RebatePolicy decorated){
		this.decorated = decorated;
	}
}
