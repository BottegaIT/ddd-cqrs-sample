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

    private String maxPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }
}
