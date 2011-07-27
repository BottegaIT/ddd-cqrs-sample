/**
 * 
 */
package pl.com.bottega.erp.sales.infrastructure.repositories.jpa;

import javax.inject.Inject;

import pl.com.bottega.ddd.domain.annotations.DomainRepositoryImpl;
import pl.com.bottega.ddd.domain.support.InjectorHelper;
import pl.com.bottega.ddd.infrastructure.repo.jpa.GenericJpaRepositoryForBaseEntity;
import pl.com.bottega.erp.sales.domain.Invoice;
import pl.com.bottega.erp.sales.domain.InvoiceRepository;

/**
 * @author Slawek
 * 
 */
@DomainRepositoryImpl
public class JpaInvoiceRepository extends GenericJpaRepositoryForBaseEntity<Invoice> implements InvoiceRepository {
  
    @Inject
    private InjectorHelper injector;

    @Override
    public Invoice load(Long orderId) {
    	Invoice invoice = super.load(orderId);
        injector.injectDependencies(invoice);
        return invoice;
    }
}
