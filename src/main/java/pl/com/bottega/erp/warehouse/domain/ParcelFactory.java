package pl.com.bottega.erp.warehouse.domain;

import pl.com.bottega.ddd.domain.annotations.DomainFactory;

@DomainFactory
public class ParcelFactory {

    public Parcel createParcel(String address) {
        return new Parcel(address);
    }
}
