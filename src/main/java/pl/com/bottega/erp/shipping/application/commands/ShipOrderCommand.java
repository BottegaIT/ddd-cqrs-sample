package pl.com.bottega.erp.shipping.application.commands;

import pl.com.bottega.cqrs.command.Command;

@Command
public class ShipOrderCommand {

    private final Long orderId;

    public ShipOrderCommand(Long orderId) {
        this.orderId = orderId;

    }

    public Long getOrderId() {
        return orderId;
    }
}
