package pl.com.bottega.erp.sales.presentation;

import java.util.List;

import pl.com.bottega.ddd.domain.sharedkernel.Money;
import pl.com.bottega.erp.sales.domain.OrderedProduct;

/**
 * Order details as seen by the client creating it.
 * 
 * @author Rafał Jamróz
 */
public class UnconfirmedOrderDetailsDto {
    private Long orderId;
    private Money totalCost;

    /**
     * TODO change to a type from presentation model
     */
    private List<OrderedProduct> orderedProducts;

    public UnconfirmedOrderDetailsDto() {
    }

    public UnconfirmedOrderDetailsDto(Long orderId, Money totalCost, List<OrderedProduct> orderedProducts) {
        this.orderId = orderId;
        this.totalCost = totalCost;
        this.orderedProducts = orderedProducts;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Money getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Money totalCost) {
        this.totalCost = totalCost;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }
}
