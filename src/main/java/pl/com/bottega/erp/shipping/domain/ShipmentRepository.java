package pl.com.bottega.erp.shipping.domain;

import pl.com.bottega.ddd.domain.annotations.DomainRepository;

/**
 * @author Rafał Jamróz
 */
@DomainRepository
public interface ShipmentRepository {
    void persist(Shipment shipment);

    Shipment save(Shipment shipment);

    Shipment load(Long orderId);
}
