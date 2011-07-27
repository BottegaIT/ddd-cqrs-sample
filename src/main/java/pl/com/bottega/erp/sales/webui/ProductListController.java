package pl.com.bottega.erp.sales.webui;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.com.bottega.erp.sales.application.ClientBasket;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;

@Controller
public class ProductListController {

    @Inject
    private ProductFinder productFinder;
    @Inject
    private ClientBasket basket;

    @RequestMapping("/")
    public String productsList(Model model) {
        model.addAttribute("products", productFinder.findProducts(new ProductSearchCriteria()));
        return "productsList";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public Void sendProductsToBasket(@RequestParam("productId") Long productId) {
        basket.addProduct(productId);
        return null;
    }
}
