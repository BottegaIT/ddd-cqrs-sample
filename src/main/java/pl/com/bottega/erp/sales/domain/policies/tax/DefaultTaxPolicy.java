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
public class DefaultTaxPolicy implements TaxPolicy{
				
	
	@Override
	public Tax calculateTax(ProductType productType, Money net) {
		BigDecimal ratio;
		String desc;
		
		switch (productType) {
		case DRUG:
			ratio = BigDecimal.valueOf(0.05);
			desc = "5% (D)";
			break;
		case FOOD:
			ratio = BigDecimal.valueOf(0.07);
			desc = "7% (F)";
			break;
		case STANDARD:
			ratio = BigDecimal.valueOf(0.23);
			desc = "23%";
			break;
			
		default:
			throw new IllegalArgumentException(productType + " not handled");
		}
				
		Money tax = net.multiplyBy(ratio);
		
		return new Tax(tax, desc);
	}

}
