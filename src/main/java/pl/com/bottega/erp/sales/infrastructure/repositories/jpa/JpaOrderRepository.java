/**
 * 
 */
package pl.com.bottega.erp.sales.infrastructure.repositories.jpa;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.com.bottega.ddd.domain.DomainEventPublisher;
import pl.com.bottega.ddd.domain.annotations.DomainRepositoryImpl;
import pl.com.bottega.ddd.domain.support.InjectorHelper;
import pl.com.bottega.erp.sales.domain.Order;
import pl.com.bottega.erp.sales.domain.OrderRepository;
import pl.com.bottega.erp.sales.domain.RebatePolicyFactory;
import pl.com.bottega.erp.sales.domain.events.OrderCreatedEvent;

/**
 * @author Slawek
 * 
 */
@DomainRepositoryImpl
public class JpaOrderRepository implements OrderRepository {

	
    @Inject
    private RebatePolicyFactory rebatePolicyFactory;
    @Inject
    private InjectorHelper injector;
    @Inject DomainEventPublisher eventPublisher;
    
	@PersistenceContext
    protected EntityManager entityManager;
    
    @Override
    public void persist(Order order) {    	
    	entityManager.persist(order);
    	 eventPublisher.publish(new OrderCreatedEvent(order.getEntityId()));
    }

    @Override
    public Order load(Long orderId) {
        Order order = entityManager.find(Order.class, orderId);
        injector.injectDependencies(order);
        order.setRebatePolicy(rebatePolicyFactory.createRebatePolicy());
        return order;
    }

	@Override
	public Order save(Order order) {
		entityManager.merge(order);
		return order;
	}

}
