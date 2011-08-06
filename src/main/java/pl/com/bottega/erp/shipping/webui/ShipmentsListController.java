package pl.com.bottega.erp.shipping.webui;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.erp.shipping.application.commands.DeliverShipmentCommand;
import pl.com.bottega.erp.shipping.application.commands.SendShipmentCommand;
import pl.com.bottega.erp.shipping.presentation.ShipmentDto;
import pl.com.bottega.erp.shipping.presentation.ShipmentFinder;

@Controller
@RequestMapping("/shipping/shipment")
public class ShipmentsListController {

    @Inject
    private ShipmentFinder finder;

    @Inject
    private Gate gate;

    @RequestMapping("/list")
    public String shippingList(Model model) {
        List<ShipmentDto> shipments = finder.findShipment();
        model.addAttribute("shipments", shipments);
        return "/shipping/shipmentsList";
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String shipOrder(@RequestParam("shipmentId") Long shipmentId) {
        gate.dispatch(new SendShipmentCommand(shipmentId));
        return "redirect:/shipping/shipment/list";
    }

    @RequestMapping(value = "/deliver", method = RequestMethod.POST)
    public String receiveShipment(@RequestParam("shipmentId") Long shipmentId) {
        gate.dispatch(new DeliverShipmentCommand(shipmentId));
        return "redirect:/shipping/shipment/list";
    }
}
