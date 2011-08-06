package pl.com.bottega.erp.shipping.application.commands;

import pl.com.bottega.cqrs.command.Command;

@Command
public class SendShipmentCommand {

    private final Long shipmentId;

    public SendShipmentCommand(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getShipmentId() {
        return shipmentId;
    }
}
