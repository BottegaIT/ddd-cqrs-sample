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
package pl.com.bottega.erp.sales.application.commands.handlers;

import java.util.Map;

import javax.inject.Inject;

import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.cqrs.command.handler.CommandHandlerAnnotation;
import pl.com.bottega.ddd.application.ApplicationEventPublisher;
import pl.com.bottega.ddd.application.SystemUser;
import pl.com.bottega.erp.sales.application.commands.CreateOrderCommand;
import pl.com.bottega.erp.sales.application.events.ProductAddedToOrderEvent;
import pl.com.bottega.erp.sales.application.services.PurchaseApplicationService;
import pl.com.bottega.erp.sales.domain.Client;
import pl.com.bottega.erp.sales.domain.ClientRepository;
import pl.com.bottega.erp.sales.domain.Order;
import pl.com.bottega.erp.sales.domain.OrderFactory;
import pl.com.bottega.erp.sales.domain.OrderRepository;
import pl.com.bottega.erp.sales.domain.ProductRepository;
import pl.com.bottega.erp.sales.domain.errors.OrderCreationException;

/**
 * @see PurchaseApplicationService
 * 
 * @author Rafał Jamróz
 */
@CommandHandlerAnnotation
public class CreateOrderCommandHandler implements
		CommandHandler<CreateOrderCommand, Long> {

	@Inject
	private OrderFactory orderFactory;

	@Inject
	private OrderRepository orderRepository;

	@Inject
	private ProductRepository productRepository;

	@Inject
	private ClientRepository clientRepository;

	@Inject
	private SystemUser systemUser;

	@Inject
	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public Long handle(CreateOrderCommand command) {
		Client currentClient = clientRepository.load(systemUser.getUserId());

		Order order = orderFactory.crateOrder(currentClient);
		
		for (Map.Entry<Long, Integer> productIdWithCount : command.getProductIdsWithCounts().entrySet()) {
			Long productId = productIdWithCount.getKey();
			Integer count = productIdWithCount.getValue();
			
			order.addProduct(productRepository.load(productId), count);
			
			applicationEventPublisher.publish(new ProductAddedToOrderEvent(productId, systemUser.getUserId(), 1));
		}
		
		orderRepository.persist(order);
		
		return order.getEntityId();
	}
}
