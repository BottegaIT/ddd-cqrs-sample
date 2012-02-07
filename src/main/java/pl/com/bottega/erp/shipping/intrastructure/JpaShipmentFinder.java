package pl.com.bottega.erp.shipping.intrastructure;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.com.bottega.cqrs.query.annotations.Finder;
import pl.com.bottega.erp.shipping.presentation.ShipmentDto;
import pl.com.bottega.erp.shipping.presentation.ShipmentFinder;

@Finder
public class JpaShipmentFinder implements ShipmentFinder {

	@PersistenceContext(unitName="defaultPU")
    private EntityManager entityManager;

    @Override
    public List<ShipmentDto> findShipment() {
        String jpql = "select new pl.com.bottega.erp.shipping.presentation.ShipmentDto(s.id, s.orderId, s.status) from Shipment s";
        Query query = entityManager.createQuery(jpql);
        return query.getResultList();
    }
}
