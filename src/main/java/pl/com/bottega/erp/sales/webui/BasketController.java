package pl.com.bottega.erp.sales.webui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.com.bottega.erp.sales.application.ClientBasket;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;

/**
 * @TODO change url to /sales/basket
 * @TODO implement basket pagination
 * 
 * @author Rafał Jamróz
 */
@Controller
// TODO
@RequestMapping("/sales/products")
public class BasketController {

    @Inject
    private ProductFinder productFinder;
    @Inject
    private ClientBasket basket;

    @RequestMapping(value = "/basketItems", method = RequestMethod.GET)
    public String getBasketItems(Model model) {
        if (basket.hasItems()) {
            Map<Long, Integer> productIdsWithCounts = basket.getProductIdsWithCounts();
            List<ProductListItemDto> productsInBasket = findProductsByIds(productIdsWithCounts.keySet());
            List<BasketItemDto> basketItems = productsToBasketItems(productsInBasket, productIdsWithCounts);
            model.addAttribute("basketItems", basketItems);
        }
        return "sales/basketItems";
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

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String sendProductsToBasket(Model model, @RequestParam("productId") Long productId) {
        basket.addProduct(productId);
        return getBasketItems(model);
    }

    @RequestMapping(value = "/clearBasket", method = RequestMethod.POST)
    @ResponseBody
    public void clearBasket() {
        basket.clearBasket();
    }
}
