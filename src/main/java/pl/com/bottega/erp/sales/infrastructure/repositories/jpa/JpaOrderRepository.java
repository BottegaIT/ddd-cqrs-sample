/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.com.bottega.erp.sales.infrastructure.repositories.jpa;

import javax.inject.Inject;

import pl.com.bottega.ddd.domain.DomainEventPublisher;
import pl.com.bottega.ddd.domain.annotations.DomainRepositoryImpl;
import pl.com.bottega.ddd.domain.support.InjectorHelper;
import pl.com.bottega.ddd.infrastructure.repo.jpa.GenericJpaRepositoryForBaseEntity;
import pl.com.bottega.erp.sales.domain.Order;
import pl.com.bottega.erp.sales.domain.OrderRepository;
import pl.com.bottega.erp.sales.domain.RebatePolicyFactory;
import pl.com.bottega.erp.sales.domain.events.OrderCreatedEvent;

/**
 * @author Slawek
 * 
 */
@DomainRepositoryImpl
public class JpaOrderRepository extends GenericJpaRepositoryForBaseEntity<Order> implements OrderRepository {

    @Inject
    private RebatePolicyFactory rebatePolicyFactory;
    @Inject
    private InjectorHelper injector;
    @Inject DomainEventPublisher eventPublisher;
    
    @Override
    public void persist(Order order) {    	
    	super.persist(order);
    	 eventPublisher.publish(new OrderCreatedEvent(order.getEntityId()));
    }

    @Override
    public Order load(Long orderId) {
        Order order = super.load(orderId);
        injector.injectDependencies(order);
        order.setRebatePolicy(rebatePolicyFactory.createRebatePolicy());
        return order;
    }
}
