package pl.com.bottega.erp.shipping.webui;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.erp.shipping.application.commands.ReceiveShipmentCommand;
import pl.com.bottega.erp.shipping.application.commands.ShipOrderCommand;

@Controller
public class ShipmentController {

    @Inject
    private Gate gate;

    @RequestMapping(value = "/shipment/create", method = RequestMethod.POST)
    public String shipOrder(@RequestParam("orderId") Long orderId) {
    	gate.dispatch(new ShipOrderCommand(orderId));
        return "redirect:/order/" + orderId;
    }

    @RequestMapping(value = "/shipment/receive", method = RequestMethod.POST)
    public String receiveShipment(@RequestParam("shipmentId") Long shipmentId) {
    	gate.dispatch(new ReceiveShipmentCommand(shipmentId));
        return "redirect:/orders/";
    }
}
