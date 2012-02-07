/**
 * 
 */
package pl.com.bottega.erp.sales.presentation;

import java.io.Serializable;

import pl.com.bottega.ddd.domain.sharedkernel.Money;

/**
 * @author Slawek
 * 
 */
public class ProductListItemDto implements Serializable {
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

	public ProductListItemDto(Long productId, String displayedName, Money price) {
		super();
		this.productId = productId;
		this.displayedName = displayedName;
		this.price = price;
	}
    
    
}
