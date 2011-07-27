package pl.com.bottega.erp.sales.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.com.bottega.ddd.application.annotation.ApplicationStatefullComponent;
import pl.com.bottega.ddd.infrastructure.events.EventListener;
import pl.com.bottega.erp.sales.domain.events.OrderCreatedEvent;

/**
 * @author Rafał Jamróz
 */
@SuppressWarnings("serial")
@ApplicationStatefullComponent
public class ClientBasket implements Serializable{

    private List<Long> productIds = new ArrayList<Long>();

    @EventListener
    public void clearBasketOnSuccessfulOrderCreation(OrderCreatedEvent event) {
        clearBasket();
    }

    public void clearBasket() {
        productIds.clear();
    }

    public void addProduct(Long productId) {
        productIds.add(productId);
    }

    public List<Long> getProductIds() {
        return Collections.unmodifiableList(productIds);
    }
}
