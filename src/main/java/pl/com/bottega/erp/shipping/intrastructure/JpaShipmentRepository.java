package pl.com.bottega.erp.shipping.intrastructure;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.com.bottega.ddd.domain.annotations.DomainRepositoryImpl;
import pl.com.bottega.ddd.domain.support.InjectorHelper;
import pl.com.bottega.erp.shipping.domain.Shipment;
import pl.com.bottega.erp.shipping.domain.ShipmentRepository;

@DomainRepositoryImpl
public class JpaShipmentRepository implements ShipmentRepository {

    @Inject
    private InjectorHelper injectorHelper;

	@PersistenceContext(name="defaultPU")
    protected EntityManager entityManager;


    @Override
    public Shipment load(Long id) {
        Shipment shipping = entityManager.find(Shipment.class, id);
        injectorHelper.injectDependencies(shipping);
        return shipping;
    }

	@Override
	public void persist(Shipment shipment) {
		entityManager.persist(shipment);
		
	}

	@Override
	public Shipment save(Shipment shipment) {
		persist(shipment);
		return shipment;
	}
}
