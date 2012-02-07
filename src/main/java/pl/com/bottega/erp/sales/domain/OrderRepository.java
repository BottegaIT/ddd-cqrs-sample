package pl.com.bottega.erp.sales.domain;

import pl.com.bottega.ddd.domain.annotations.DomainRepository;

/**
 * 
 * @author Slawek
 * 
 */
@DomainRepository
public interface OrderRepository {

    void persist(Order order);

    Order save(Order order);

    Order load(Long orderId);
}
