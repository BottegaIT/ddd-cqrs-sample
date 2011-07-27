package pl.com.bottega.erp.sales.domain;

import pl.com.bottega.ddd.domain.annotations.DomainRepository;

/**
 * 
 * @author Slawek
 * 
 */
@DomainRepository
public interface OrderRepository {

    public void persist(Order order);

    public Order save(Order order);

    public Order load(Long orderId);

}
