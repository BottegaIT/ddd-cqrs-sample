package pl.com.bottega.erp.shipping.application.commands.handlers;

import javax.inject.Inject;

import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.cqrs.command.handler.CommandHandlerAnnotation;
import pl.com.bottega.ddd.domain.DomainEventPublisher;
import pl.com.bottega.erp.shipping.application.commands.ReceiveShipmentCommand;
import pl.com.bottega.erp.shipping.domain.events.ShipmentDeliveredEvent;

/**
 * TODO CommandHandler method??
 */
@CommandHandlerAnnotation
public class ReceiveShipmentCommandHandler implements CommandHandler<ReceiveShipmentCommand, Void> {

    @Inject
    private DomainEventPublisher eventPublisher;

    @Override
    public Void handle(ReceiveShipmentCommand command) {
        // let's assume everything went well ...
        eventPublisher.publish(new ShipmentDeliveredEvent(command.getShipmentId()));
        return null;
    }
}
