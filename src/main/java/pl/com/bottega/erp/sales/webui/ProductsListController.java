package pl.com.bottega.erp.sales.webui;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.erp.sales.application.ClientBasket;
import pl.com.bottega.erp.sales.application.commands.CreateOrderCommand;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;

@Controller
@RequestMapping("/sales/products")
public class ProductsListController {

    @Inject
    private ProductFinder productFinder;
    @Inject
    private ClientBasket basket;
    @Inject
    private Gate gate;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String productsList(Model model) {
        model.addAttribute("products", productFinder.findProducts(new ProductSearchCriteria()));
        return "sales/productsList";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String sendProductsToBasket(Model model, @RequestParam("productId") Long productId) {
        basket.addProduct(productId);
        return getBasketItems(model);
    }

    @RequestMapping(value = "/basketItems", method = RequestMethod.GET)
    public String getBasketItems(Model model) {
        model.addAttribute("items", basket.getProductIds());
        return "sales/basketItems";
    }

    @RequestMapping(value = "/clearBasket", method = RequestMethod.POST)
    @ResponseBody
    public void clearBasket() {
        basket.clearBasket();
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String createOrder() {
        Long orderId = (Long) gate.dispatch(new CreateOrderCommand(basket.getProductIds()));
        return "redirect:/sales/confirmOrder/" + orderId;
    }
}
