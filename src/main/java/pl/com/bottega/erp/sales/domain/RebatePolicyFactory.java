/**
 * 
 */
package pl.com.bottega.erp.sales.domain;

import javax.inject.Inject;

import pl.com.bottega.ddd.application.SystemUser;
import pl.com.bottega.ddd.domain.annotations.DomainFactory;
import pl.com.bottega.ddd.domain.sharedkernel.Money;
import pl.com.bottega.erp.sales.domain.policies.rebate.StandardRebate;
import pl.com.bottega.erp.sales.domain.policies.rebate.decorators.VipRebate;

/**
 * @author Slawek
 *
 */
@DomainFactory
public class RebatePolicyFactory {

	@Inject
	private SystemUser systemUser;
	
	public RebatePolicy createRebatePolicy() {		
		RebatePolicy rebatePolicy = new StandardRebate(10, 1);
		
		if (isVip(systemUser)){
			rebatePolicy = new VipRebate(rebatePolicy, new Money(1000.0), new Money(100));
		}
		
		return rebatePolicy;
	}
	
	/**
	 * @param systemUser
	 * @return
	 */
	private boolean isVip(SystemUser systemUser) {
		// TODO Auto-generated method stub
		return true;
	}
}
