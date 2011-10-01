package pl.com.bottega.erp.warehouse.infrastructure;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.google.common.base.Strings;

import pl.com.bottega.cqrs.query.annotations.Finder;
import pl.com.bottega.erp.warehouse.domain.Parcel;
import pl.com.bottega.erp.warehouse.presentation.ParcelDto;
import pl.com.bottega.erp.warehouse.presentation.ParcelFinder;

@Finder
public class JpaParcelFinder implements ParcelFinder {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ParcelDto get(Long packageId) {
        Parcel parcel = entityManager.find(Parcel.class, packageId);
        return toDto(parcel);
    }

    private ParcelDto toDto(Parcel parcel) {
        ParcelDto dto = new ParcelDto();
        dto.setAddress(parcel.getAddress());
        dto.setStatus(parcel.getStatus().toString());
        dto.setProductsIds(parcel.getProductsIds());
        return dto;
    }

}
