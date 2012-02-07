package pl.com.bottega.erp.sales.webui;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import pl.com.bottega.ddd.application.annotation.ApplicationStatefullComponent;
import pl.com.bottega.ddd.infrastructure.events.EventListener;
import pl.com.bottega.erp.sales.domain.events.OrderCreatedEvent;

import com.google.common.collect.Maps;

/**
 * @author Rafał Jamróz
 */
@SuppressWarnings("serial")
@ApplicationStatefullComponent
public class ClientBasket implements Serializable {

    private Map<Long, Integer> productIdsWithCounts = Maps.newHashMap();

    @EventListener
    public void clearBasketOnSuccessfulOrderCreation(OrderCreatedEvent event) {
        clearBasket();
    }

    public void clearBasket() {
        productIdsWithCounts.clear();
    }

    public void addProduct(Long productId) {
        Integer count = productIdsWithCounts.get(productId);
        if (count == null) {
            count = 0;
        }
        productIdsWithCounts.put(productId, count + 1);
    }

    public Map<Long, Integer> getProductIdsWithCounts() {
        return Collections.unmodifiableMap(productIdsWithCounts);
    }

    public boolean hasItems() {
        return !productIdsWithCounts.isEmpty();
    }
}
