/**
 * 
 */
package pl.com.bottega.erp.crm.domain;

import pl.com.bottega.ddd.domain.annotations.DomainRepository;

/**
 * @author Slawek
 * 
 */
@DomainRepository
public interface CustomerRepository {

	public Customer load(Long id);

	public Customer save(Customer entity);
}
