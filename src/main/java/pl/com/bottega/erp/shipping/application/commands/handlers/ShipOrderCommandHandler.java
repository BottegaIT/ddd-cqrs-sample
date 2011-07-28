package pl.com.bottega.erp.shipping.application.commands.handlers;

import java.util.Random;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.cqrs.command.handler.CommandHandlerAnnotation;
import pl.com.bottega.ddd.domain.DomainEventPublisher;
import pl.com.bottega.erp.shipping.application.commands.ShipOrderCommand;
import pl.com.bottega.erp.shipping.domain.events.OrderShippedEvent;

@Transactional
@CommandHandlerAnnotation
public class ShipOrderCommandHandler implements CommandHandler<ShipOrderCommand, Void> {

    @Inject
    private DomainEventPublisher eventPublisher;

    @Override
    public Void handle(ShipOrderCommand command) {
        // create shipment
        Long shipmentId = Math.abs(new Random().nextLong());
        System.out.format("shipment[id=%s] created for order[id=%s]\n", shipmentId, command.getOrderId());
        eventPublisher.publish(new OrderShippedEvent(command.getOrderId(), shipmentId));
        return null;
    }

}
