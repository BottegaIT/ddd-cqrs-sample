package pl.com.bottega.erp.shipping.application.events;

import javax.inject.Inject;

import pl.com.bottega.ddd.infrastructure.events.EventListener;
import pl.com.bottega.ddd.infrastructure.events.EventListeners;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;
import pl.com.bottega.erp.sales.presentation.ClientOrderDetailsDto;
import pl.com.bottega.erp.sales.presentation.OrderFinder;
import pl.com.bottega.erp.shipping.domain.Shipment;
import pl.com.bottega.erp.shipping.domain.ShipmentFactory;
import pl.com.bottega.erp.shipping.domain.ShipmentRepository;

/**
 * When an order is submitted by a customer automatically create a shipment in
 * WAITING status.
 * 
 * NOTICE: This is an example of communication across multiple bounded contexts
 * using events. In this context we can't access Order aggregate directly so we
 * use DTO from the read model instead.
 * 
 * @author Rafał Jamróz
 */
@EventListeners
public class OrderSubmittedForShippingListener {

    @Inject
    private ShipmentFactory factory;

    @Inject
    private OrderFinder orderFinder;

    @Inject
    private ShipmentRepository repository;

    @EventListener(asynchronous = true)
    public void handle(OrderSubmittedEvent event) {
        ClientOrderDetailsDto orderDetails = orderFinder.getClientOrderDetails(event.getOrderId());
        Shipment shipment = factory.createShipment(orderDetails);
        repository.persist(shipment);
    }
}
