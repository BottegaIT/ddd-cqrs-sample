package pl.com.bottega.erp.sales.application.commands.handlers;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.cqrs.command.handler.CommandHandlerAnnotation;
import pl.com.bottega.erp.sales.application.commands.AddProductToOrderCommand;
import pl.com.bottega.erp.sales.domain.Order;
import pl.com.bottega.erp.sales.domain.OrderRepository;
import pl.com.bottega.erp.sales.domain.Product;
import pl.com.bottega.erp.sales.domain.ProductRepository;

@CommandHandlerAnnotation
@Named
@Stateless
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
