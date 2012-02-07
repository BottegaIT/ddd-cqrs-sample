package pl.com.bottega.erp.sales.application.commands;

import java.io.Serializable;
import java.util.Map;

import pl.com.bottega.cqrs.command.Command;

@Command
public class CreateOrderCommand implements Serializable{

    private final Map<Long, Integer> productIdsWithCounts;

    public CreateOrderCommand(Map<Long, Integer> productIdsWithCounts) {
        this.productIdsWithCounts = productIdsWithCounts;
    }

    public Map<Long, Integer> getProductIdsWithCounts() {
        return productIdsWithCounts;
    }
}
