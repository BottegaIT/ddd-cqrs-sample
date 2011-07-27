package pl.com.bottega.erp.sales.presentation;

import java.util.Date;
import java.util.List;

import pl.com.bottega.ddd.domain.sharedcernel.Money;
import pl.com.bottega.erp.sales.domain.Order.OrderStatus;
import pl.com.bottega.erp.sales.domain.OrderedProduct;

/**
 * Order details as seen by the client creating it.
 * 
 * @author Rafał Jamróz
 */
public class ClientOrderDetailsDto {
    private Long orderId;
    private Money totalCost;
    private Date submitDate;
    private OrderStatus status;
    private List<OrderedProduct> orderedProducts;

    public ClientOrderDetailsDto() {
    }
    
    public ClientOrderDetailsDto(Long orderId, Money totalCost, Date submitDate, OrderStatus status,
            List<OrderedProduct> orderedProducts) {
        this.orderId = orderId;
        this.totalCost = totalCost;
        this.submitDate = submitDate;
        this.status = status;
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

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }
}
