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

import javax.inject.Inject;

import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.cqrs.command.handler.CommandHandlerAnnotation;
import pl.com.bottega.erp.sales.application.commands.AddProductToOrderCommand;
import pl.com.bottega.erp.sales.domain.Order;
import pl.com.bottega.erp.sales.domain.OrderRepository;
import pl.com.bottega.erp.sales.domain.Product;
import pl.com.bottega.erp.sales.domain.ProductRepository;

@CommandHandlerAnnotation
public class AddProductToOrderCommandHandler implements CommandHandler<AddProductToOrderCommand, Void>{

	@Inject
	private OrderRepository orderRepository;

	@Inject
	private ProductRepository productRepository;

	
	@Override
	public Void handle(AddProductToOrderCommand command) {
		Product product = productRepository.load(command.getProductId());
		Order order = orderRepository.load(command.getOrderId());
		
		order.addProduct(product, command.getQuantity());
		
		orderRepository.save(order);
		
		return null;
	}

}
