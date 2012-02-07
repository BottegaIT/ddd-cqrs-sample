package pl.com.bottega.erp.sales.domain;

import pl.com.bottega.ddd.domain.annotations.DomainRepository;

/**
 * 
 * @author Slawek
 *
 */
@DomainRepository
public interface InvoiceRepository {

	/**
	 * @param invoice
	 */
	public Invoice save(Invoice invoice);

}
