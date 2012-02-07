package pl.com.bottega.erp.sales.saga;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrderShipmentStatusTrackerData {

    @Id
    @GeneratedValue
    private Long id;

    private Long orderId;

    private Long shipmentId;

    private Boolean shipmentReceived = false;

    public Long getOrderId() {
        return orderId;
    }

    /**
     * Id of order aggregate.
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * Id of shipment aggregate (from shipment module) once it's created.
     */
    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Boolean getShipmentReceived() {
        return shipmentReceived;
    }

    public void setShipmentReceived(Boolean shipmentReceived) {
        this.shipmentReceived = shipmentReceived;
    }
}
