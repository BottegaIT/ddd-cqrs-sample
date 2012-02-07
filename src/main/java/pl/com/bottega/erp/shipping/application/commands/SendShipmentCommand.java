package pl.com.bottega.erp.shipping.application.commands;

import java.io.Serializable;

import pl.com.bottega.cqrs.command.Command;

@Command
public class SendShipmentCommand implements Serializable {

    private final Long shipmentId;

    public SendShipmentCommand(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getShipmentId() {
        return shipmentId;
    }
}
