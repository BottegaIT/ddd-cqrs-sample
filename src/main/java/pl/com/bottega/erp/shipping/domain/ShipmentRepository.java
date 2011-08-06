package pl.com.bottega.erp.shipping.domain;

import pl.com.bottega.ddd.domain.annotations.DomainRepository;

/**
 * @author Rafał Jamróz
 */
@DomainRepository
public interface ShipmentRepository {
    void persist(Shipment order);

    Shipment save(Shipment order);

    Shipment load(Long orderId);
}
