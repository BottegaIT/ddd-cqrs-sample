package pl.com.bottega.erp.sales.presentation.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.com.bottega.cqrs.query.annotations.Finder;
import pl.com.bottega.erp.sales.domain.Order;
import pl.com.bottega.erp.sales.domain.Order.OrderStatus;
import pl.com.bottega.erp.sales.presentation.ClientOrderListItemDto;
import pl.com.bottega.erp.sales.presentation.OrderDetailsDto;
import pl.com.bottega.erp.sales.presentation.OrderFinder;
import pl.com.bottega.erp.sales.presentation.UnconfirmedOrderDetailsDto;

/**
 * TODO basic security checks
 * 
 * @author Rafał Jamróz
 */
@Finder
public class JpaOrderFinder implements OrderFinder {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UnconfirmedOrderDetailsDto getUnconfirmedOrderDetails(Long orderId) {
        Order order = entityManager.find(Order.class, orderId);
        if (order != null && order.getStatus() == OrderStatus.DRAFT) {
            return toUnconfirmedOrderDetailsDto(order);
        }
        return null;
    }

    @Override
    public OrderDetailsDto getOrderDetails(Long orderId) {
        Order order = entityManager.find(Order.class, orderId);
        return order == null ? null : toOrderDetailsDto(order);
    }

    private UnconfirmedOrderDetailsDto toUnconfirmedOrderDetailsDto(Order order) {
        UnconfirmedOrderDetailsDto dto = new UnconfirmedOrderDetailsDto();
        dto.setOrderId(order.getEntityId());
        dto.setTotalCost(order.getTotalCost());
        dto.setOrderedProducts(order.getOrderedProducts());
        return dto;
    }

    private OrderDetailsDto toOrderDetailsDto(Order order) {
        OrderDetailsDto dto = new OrderDetailsDto();
        dto.setOrderId(order.getEntityId());
        dto.setTotalCost(order.getTotalCost());
        dto.setOrderedProducts(order.getOrderedProducts());
        dto.setSubmitDate(order.getSubmitDate());
        dto.setOrderStatus(order.getStatus());
        return dto;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ClientOrderListItemDto> findOrders() {
        Query query = entityManager
                .createQuery("select new pl.com.bottega.erp.sales.presentation.ClientOrderListItemDto("
                        + "o.id, o.totalCost, o.submitDate, o.status) from Order o");
        return query.getResultList();
    }
}
