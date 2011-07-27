package pl.com.bottega.erp.sales.domain;

import pl.com.bottega.ddd.domain.annotations.DomainPolicy;
import pl.com.bottega.ddd.domain.sharedcernel.Money;

/**
 * 
 * @author Slawek
 *
 */
@DomainPolicy
public interface RebatePolicy {

	public Money calculateRebate(Product product, int quantity, Money regularCost);

}
