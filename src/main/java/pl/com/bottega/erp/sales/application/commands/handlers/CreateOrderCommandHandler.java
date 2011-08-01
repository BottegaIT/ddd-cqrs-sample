package pl.com.bottega.erp.sales.application.commands.handlers;

import javax.annotation.Resource;

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
public class CreateOrderCommandHandler implements CommandHandler<CreateOrderCommand, Long> {

    @Resource
    private OrderFactory orderFactory;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private ProductRepository productRepository;

    @Resource
    private ClientRepository clientRepository;

    @Resource
    private SystemUser systemUser;

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Long handle(CreateOrderCommand command) {
        Client currentClient = clientRepository.load(systemUser.getUserId());
        try {
            Order order = orderFactory.crateOrder(currentClient);
            for (Long productId : command.getProductIds()) {
                order.addProduct(productRepository.load(productId), 1);
                applicationEventPublisher.publish(new ProductAddedToOrderEvent(productId, systemUser.getUserId(), 1));
            }
            orderRepository.persist(order);
            return order.getId();
        } catch (OrderCreationException e) {
            throw new RuntimeException(e);
        }
    }
}
