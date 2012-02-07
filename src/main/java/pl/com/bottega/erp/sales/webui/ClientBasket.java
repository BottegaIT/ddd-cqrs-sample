/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
