package pl.com.bottega.erp.sales.presentation.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.com.bottega.cqrs.query.annotations.Finder;
import pl.com.bottega.erp.sales.domain.Order;
import pl.com.bottega.erp.sales.presentation.ClientOrderDetailsDto;
import pl.com.bottega.erp.sales.presentation.ClientOrderListItemDto;
import pl.com.bottega.erp.sales.presentation.OrderFinder;

/**
 * @author Rafał Jamróz
 */
@Finder
public class JpaOrderFinder implements OrderFinder {

	@PersistenceContext
    private EntityManager entityManager;

    @Override
    public ClientOrderDetailsDto getClientOrderDetails(Long orderId) {
        Order order = entityManager.find(Order.class, orderId);
        return order == null ? null : toOrderDetailsDto(order);
    }

    private ClientOrderDetailsDto toOrderDetailsDto(Order order) {
        ClientOrderDetailsDto dto = new ClientOrderDetailsDto();
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
