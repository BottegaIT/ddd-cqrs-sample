package pl.com.bottega.erp.shipping.application.commands;

import pl.com.bottega.cqrs.command.Command;

@Command
public class ReceiveShipmentCommand {

    private final Long shipmentId;

    public ReceiveShipmentCommand(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getShipmentId() {
        return shipmentId;
    }
}
