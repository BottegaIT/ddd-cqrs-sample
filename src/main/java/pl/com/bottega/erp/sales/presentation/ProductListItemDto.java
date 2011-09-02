/**
 * 
 */
package pl.com.bottega.erp.sales.presentation;

import pl.com.bottega.ddd.domain.sharedkernel.Money;

/**
 * @author Slawek
 * 
 */
public class ProductListItemDto {
    private Long productId;
    private String displayedName;
    private Money price;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String productDisplayedName) {
        this.displayedName = productDisplayedName;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }
}
