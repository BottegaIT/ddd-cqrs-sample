package pl.com.bottega.erp.warehouse.application;

import javax.inject.Inject;

import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.cqrs.command.handler.CommandHandlerAnnotation;
import pl.com.bottega.erp.warehouse.domain.Parcel;
import pl.com.bottega.erp.warehouse.domain.ParcelFactory;
import pl.com.bottega.erp.warehouse.domain.ParcelRepository;

@CommandHandlerAnnotation
public class PrepareParcelCommandHandler implements CommandHandler<PrepareParcelCommand, Long> {

    @Inject
    private ParcelFactory parcelFactory;
    @Inject
    private ParcelRepository parcelRepository;

    @Override
    public Long handle(PrepareParcelCommand command) {
        Parcel parcel = parcelFactory.createParcel(command.getAddress());
        parcel.complete();
        parcel.setProductsIds(command.getProductsIds());
        parcelRepository.persist(parcel);
        return parcel.getId();
    }
}
