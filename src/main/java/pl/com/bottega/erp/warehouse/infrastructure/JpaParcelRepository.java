package pl.com.bottega.erp.warehouse.infrastructure;

import pl.com.bottega.ddd.domain.annotations.DomainRepositoryImpl;
import pl.com.bottega.ddd.infrastructure.repo.jpa.GenericJpaRepository;
import pl.com.bottega.erp.warehouse.domain.Parcel;
import pl.com.bottega.erp.warehouse.domain.ParcelRepository;

@DomainRepositoryImpl
public class JpaParcelRepository extends GenericJpaRepository<Parcel, Long> implements ParcelRepository {

}
