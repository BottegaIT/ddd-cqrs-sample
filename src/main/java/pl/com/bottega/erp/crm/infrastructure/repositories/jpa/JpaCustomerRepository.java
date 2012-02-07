/**
 * 
 */
package pl.com.bottega.erp.crm.infrastructure.repositories.jpa;

import javax.inject.Inject;

import pl.com.bottega.ddd.domain.annotations.DomainRepositoryImpl;
import pl.com.bottega.ddd.domain.support.InjectorHelper;
import pl.com.bottega.erp.crm.domain.Customer;
import pl.com.bottega.erp.crm.domain.CustomerRepository;

/**
 * @author Slawek
 *
 */
@DomainRepositoryImpl
public class JpaCustomerRepository  implements CustomerRepository{

	@Inject
	private InjectorHelper injectorHelper;
	
	@Override
	public Customer load(Long id) {		
		Customer customer = null; // TODO
		injectorHelper.injectDependencies(customer);
		return customer;
	}

	@Override
	public Customer save(Customer entity) {
		// TODO Auto-generated method stub
		return null; // TODO
	}
}
