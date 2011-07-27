package pl.com.bottega.erp.sales.application.commands;

import java.util.List;

import pl.com.bottega.cqrs.command.Command;

@Command
public class CreateOrderCommand {

    private final List<Long> productIds;

    public CreateOrderCommand(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
