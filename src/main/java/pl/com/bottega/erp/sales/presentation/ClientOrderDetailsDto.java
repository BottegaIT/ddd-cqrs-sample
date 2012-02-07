package pl.com.bottega.erp.sales.presentation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import pl.com.bottega.ddd.domain.sharedkernel.Money;
import pl.com.bottega.erp.sales.domain.Order.OrderStatus;
import pl.com.bottega.erp.sales.domain.OrderedProduct;

public class ClientOrderDetailsDto implements Serializable {

    private Long orderId;
    private Money totalCost;

    /**
     * TODO change to a type from presentation model
     */
    private List<OrderedProduct> orderedProducts;

    /**
     * TODO change to a type from presentation model
     */
    private OrderStatus orderStatus;

    private Date submitDate;

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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }
}
