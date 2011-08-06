package pl.com.bottega.erp.shipping.application.commands.handlers;

import javax.inject.Inject;

import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.cqrs.command.handler.CommandHandlerAnnotation;
import pl.com.bottega.erp.shipping.application.commands.DeliverShipmentCommand;
import pl.com.bottega.erp.shipping.domain.Shipment;
import pl.com.bottega.erp.shipping.domain.ShipmentRepository;

@CommandHandlerAnnotation
public class DeliverShipmentCommandHandler implements CommandHandler<DeliverShipmentCommand, Void> {

    @Inject
    private ShipmentRepository repository;

    @Override
    public Void handle(DeliverShipmentCommand command) {
        Shipment shipment = repository.load(command.getShipmentId());
        shipment.deliver();
        return null;
    }
}
