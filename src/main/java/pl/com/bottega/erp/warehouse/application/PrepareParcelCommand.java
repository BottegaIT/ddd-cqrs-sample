package pl.com.bottega.erp.warehouse.application;

import java.util.Set;

public class PrepareParcelCommand {

    private final Set<Long> productsIds;
    private final String address;

    public PrepareParcelCommand(Set<Long> productsIds, String address) {
        this.productsIds = productsIds;
        this.address = address;
    }

    public Set<Long> getProductsIds() {
        return productsIds;
    }

    public String getAddress() {
        return address;
    }
}
