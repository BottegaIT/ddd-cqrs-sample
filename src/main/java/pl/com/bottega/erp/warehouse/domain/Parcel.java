package pl.com.bottega.erp.warehouse.domain;

import static java.util.Collections.unmodifiableSet;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import pl.com.bottega.ddd.domain.annotations.DomainAggregateRoot;

@DomainAggregateRoot
@Entity
public class Parcel {

    public enum ParcelStatus {
        DRAFT, COMPLETE;
    }

    private String address;
    @Enumerated
    private ParcelStatus status = ParcelStatus.DRAFT;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> productsIds = new HashSet<Long>();

    protected Parcel() {
    }

    public Parcel(String address) {
        this.address = address;
    }

    public void complete() {
        status = ParcelStatus.COMPLETE;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public Set<Long> getProductsIds() {
        return unmodifiableSet(productsIds);
    }

    public void setProductsIds(Set<Long> productsIds) {
        this.productsIds = productsIds;
    }

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }
}
