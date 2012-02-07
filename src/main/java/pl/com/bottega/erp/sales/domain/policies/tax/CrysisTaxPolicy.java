package pl.com.bottega.erp.sales.domain.policies.tax;

import java.math.BigDecimal;

import pl.com.bottega.ddd.domain.annotations.DomainPolicyImpl;
import pl.com.bottega.ddd.domain.sharedkernel.Money;
import pl.com.bottega.erp.sales.domain.Product.ProductType;
import pl.com.bottega.erp.sales.domain.Tax;
import pl.com.bottega.erp.sales.domain.TaxPolicy;

/**
 * Sample Policy impl<br> 
 * 
 * @author Slawek
 *
 */
@DomainPolicyImpl
public class CrysisTaxPolicy implements TaxPolicy{
				
	
	@Override
	public Tax calculateTax(ProductType productType, Money net) {
		BigDecimal ratio = BigDecimal.valueOf(0.4);
		String desc = "sorry";				
				
		Money tax = net.multiplyBy(ratio);
		
		return new Tax(tax, desc);
	}

}
