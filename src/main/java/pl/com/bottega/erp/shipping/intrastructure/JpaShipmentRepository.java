package pl.com.bottega.erp.shipping.intrastructure;

import javax.inject.Inject;

import pl.com.bottega.ddd.domain.annotations.DomainRepositoryImpl;
import pl.com.bottega.ddd.domain.support.InjectorHelper;
import pl.com.bottega.ddd.infrastructure.repo.jpa.GenericJpaRepositoryForBaseEntity;
import pl.com.bottega.erp.shipping.domain.Shipment;
import pl.com.bottega.erp.shipping.domain.ShipmentRepository;

@DomainRepositoryImpl
public class JpaShipmentRepository extends GenericJpaRepositoryForBaseEntity<Shipment> implements ShipmentRepository {

    @Inject
    private InjectorHelper injectorHelper;

    // TODO move to GenericJpaRepositoryForBaseEntity
    @Override
    public Shipment load(Long id) {
        Shipment shipping = super.load(id);
        injectorHelper.injectDependencies(shipping);
        return shipping;
    }
}
