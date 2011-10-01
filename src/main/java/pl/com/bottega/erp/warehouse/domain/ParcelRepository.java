package pl.com.bottega.erp.warehouse.domain;

import pl.com.bottega.ddd.domain.annotations.DomainRepository;

@DomainRepository
public interface ParcelRepository {

    void persist(Parcel parcel);

}
