/**
 * 
 */
package pl.com.bottega.erp.sales.presentation;

/**
 * @author Slawek
 * 
 */
public class ProductSearchCriteria {

    private String name;

    private String description;

    // Good enough because value is not used in calculations
    private Double maxPrice;

    public boolean hasConstraints() {
        return name != null || description != null || maxPrice != null;
    }

    public boolean hasOrder() {
        // TODO add ordering
        return false;
    }

    public ProductSearchCriteria maxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public ProductSearchCriteria containsText(String text) {
        this.name = text;
        this.description = text;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

}
