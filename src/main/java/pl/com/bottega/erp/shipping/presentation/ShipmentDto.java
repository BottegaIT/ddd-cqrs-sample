package pl.com.bottega.erp.shipping.presentation;

import java.io.Serializable;

import pl.com.bottega.erp.shipping.domain.ShippingStatus;

public class ShipmentDto implements Serializable {

    private Long shipmentId;
    private Long orderId;
    private ShippingStatus status;

    public ShipmentDto(Long shipmentId, Long orderId, ShippingStatus status) {
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.status = status;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public ShippingStatus getStatus() {
        return status;
    }

    public void setStatus(ShippingStatus status) {
        this.status = status;
    }
}
