package pl.com.bottega.erp.sales.presentation;

import java.io.Serializable;
import java.util.Date;

import pl.com.bottega.ddd.domain.sharedkernel.Money;
import pl.com.bottega.erp.sales.domain.Order.OrderStatus;

/**
 * Orders as seen by client creating it on a list.
 * 
 * @author Rafał Jamróz
 */
public class ClientOrderListItemDto implements Serializable {

    private Long orderId;
    private Money totalCost;
    private Date submitDate;
    private OrderStatus status;

    public ClientOrderListItemDto() {
    }

    public ClientOrderListItemDto(Long orderId, Money totalCost, Date submitDate, OrderStatus status) {
        this.orderId = orderId;
        this.totalCost = totalCost;
        this.submitDate = submitDate;
        this.status = status;
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
}
