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
