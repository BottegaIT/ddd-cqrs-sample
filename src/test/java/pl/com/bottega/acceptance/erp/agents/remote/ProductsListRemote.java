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
package pl.com.bottega.acceptance.erp.agents.remote;

import javax.inject.Inject;

import pl.com.bottega.acceptance.commons.RemoteAgent;
import pl.com.bottega.acceptance.erp.agents.ProductsListAgent;
import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.cqrs.query.PaginatedResult;
import pl.com.bottega.erp.sales.application.commands.CreateOrderCommand;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;

/**
 * @author Rafał Jamróz
 */
@RemoteAgent
public class ProductsListRemote implements ProductsListAgent {

    /**
     * Order in the scenario context. Order is created after successful checkout
     * and its id is stored here.
     */
    @Inject
    private CurrentOrder currentOrder;

    @Inject
    private BasketItems basketItems;

    @Inject
    private Gate gate;

    @Inject
    private ProductFinder productFinder;

    @Override
    public void checkout() {
        Long orderId = (Long) gate.dispatch(new CreateOrderCommand(basketItems.getProductsIdsWithCounts()));
        currentOrder.setOrderId(orderId);
    }

    @Override
    public int getBasketItemsCount() {
        return basketItems.getProductsIdsWithCounts().keySet().size();
    }

    @Override
    public void addAnyProductToBasket() {
        PaginatedResult<ProductListItemDto> products = productFinder.findProducts(new ProductSearchCriteria());
        ProductListItemDto anyProduct = products.getItems().get(0);
        basketItems.getProductsIdsWithCounts().put(anyProduct.getProductId(), 1);
    }

    @Override
    public boolean productsExist() {
        return productFinder.findProducts(new ProductSearchCriteria()).getTotalItemsCount() > 0;
    }
}
