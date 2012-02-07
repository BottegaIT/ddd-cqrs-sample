package pl.com.bottega.erp.shipping.domain;

import javax.inject.Inject;

import pl.com.bottega.ddd.domain.annotations.DomainFactory;
import pl.com.bottega.ddd.domain.support.InjectorHelper;
import pl.com.bottega.erp.sales.presentation.ClientOrderDetailsDto;

@DomainFactory
public class ShipmentFactory {

    @Inject
    private InjectorHelper helper;

    public Shipment createShipment(ClientOrderDetailsDto orderDetails) {
        Shipment shipment = new Shipment(orderDetails.getOrderId());
        helper.injectDependencies(shipment);
        return shipment;
    }
}
