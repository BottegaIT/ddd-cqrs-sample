package pl.com.bottega.erp.sales.webui;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.com.bottega.erp.sales.presentation.OrderFinder;

@Controller
@RequestMapping("/sales/orders")
public class OrdersListController {
    @Inject
    private OrderFinder orderFinder;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String ordersList(Model model) {
        model.addAttribute("orders", orderFinder.findCurrentClientsOrders());
        return "sales/ordersList";
    }
}
