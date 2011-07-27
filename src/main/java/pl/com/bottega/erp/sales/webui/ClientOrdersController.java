package pl.com.bottega.erp.sales.webui;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.erp.sales.application.ClientBasket;
import pl.com.bottega.erp.sales.application.commands.CreateOrderCommand;
import pl.com.bottega.erp.sales.application.commands.SubmitOrderCommand;
import pl.com.bottega.erp.sales.presentation.OrderFinder;

@Controller
public class ClientOrdersController {

    @Inject
    private Gate gate;
    @Inject
    private OrderFinder orderFinder;
    @Inject
    private ClientBasket basket;

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String createOrder() {
        Long orderId = (Long) gate.dispatch(new CreateOrderCommand(basket.getProductIds()));
        return "redirect:/order/" + orderId;
    }

    @RequestMapping("/orders")
    public String ordersList(Model model) {
        model.addAttribute("orders", orderFinder.findCurrentClientsOrders());
        return "ordersList";
    }

    @RequestMapping("/order/{orderId}")
    public String orderDetails(Model model, @PathVariable("orderId") Long orderId) {
        model.addAttribute("order", orderFinder.getDetails(orderId));
        return "orderDetails";
    }

    @RequestMapping(value = "/order/{orderId}/submit", method = RequestMethod.POST)
    public String submitOrder(@PathVariable("orderId") Long orderId) {
        gate.dispatch(new SubmitOrderCommand(orderId));
        return "redirect:/order/" + orderId;
    }
}
