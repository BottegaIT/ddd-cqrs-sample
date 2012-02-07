/**
 * 
 */
package pl.com.bottega.erp.sales.infrastructure.repositories.jpa;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.com.bottega.ddd.domain.annotations.DomainRepositoryImpl;
import pl.com.bottega.ddd.domain.support.InjectorHelper;
import pl.com.bottega.erp.sales.domain.Invoice;
import pl.com.bottega.erp.sales.domain.InvoiceRepository;

/**
 * @author Slawek
 * 
 */
@DomainRepositoryImpl
public class JpaInvoiceRepository implements InvoiceRepository {
  
    @Inject
    private InjectorHelper injector;
    
	@PersistenceContext
    protected EntityManager entityManager;

    private Class<Invoice> clazz;

	public Invoice load(Long id) {
        Invoice invoice= entityManager.find(clazz, id);
        injector.injectDependencies(invoice);
        return invoice;
    }
    
    @Override
	public Invoice save(Invoice entity) {
        return entityManager.merge(entity);
    }
}
