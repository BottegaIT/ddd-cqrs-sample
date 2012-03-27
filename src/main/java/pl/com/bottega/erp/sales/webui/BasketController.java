package pl.com.bottega.erp.sales.webui;

import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ManagedBean(name="basket")
public class BasketController
{
    @Inject
    private ProductFinder productFinder;
    @Inject
    private ClientBasket basket;

    public List<BasketItemDto> getBasketItems() {
        if (basket.hasItems()) {
            Map<Long, Integer> productIdsWithCounts = basket.getProductIdsWithCounts();
            List<ProductListItemDto> productsInBasket = findProductsByIds(productIdsWithCounts.keySet());
            List<BasketItemDto> basketItems = productsToBasketItems(productsInBasket, productIdsWithCounts);

            return basketItems;
        }
        return new ArrayList<BasketItemDto>();
    }

    private List<ProductListItemDto> findProductsByIds(Set<Long> productsIds) {
        ProductSearchCriteria criteria = new ProductSearchCriteria();
        criteria.setSpecificProductIds(productsIds);
        return productFinder.findProducts(criteria).getItems();
    }

    private List<BasketItemDto> productsToBasketItems(List<ProductListItemDto> products,
                                                      Map<Long, Integer> productIdsWithCounts) {
        List<BasketItemDto> basketItems = new ArrayList<BasketItemDto>();
        for (ProductListItemDto product : products) {
            Long productId = product.getProductId();
            Integer count = productIdsWithCounts.get(productId);
            basketItems.add(new BasketItemDto(productId, product.getDisplayedName(), count));
        }
        return basketItems;
    }


    public void sendProductsToBasket(Long productId) {
        basket.addProduct(productId);
    }

    public void clearBasket() {
        basket.clearBasket();
    }

    public boolean isEmptyBasket()
    {
        return !basket.hasItems();
    }
}
