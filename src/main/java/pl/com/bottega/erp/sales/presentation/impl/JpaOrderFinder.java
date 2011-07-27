package pl.com.bottega.erp.sales.presentation.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.com.bottega.cqrs.query.annotations.Finder;
import pl.com.bottega.erp.sales.domain.Order;
import pl.com.bottega.erp.sales.presentation.ClientOrderDetailsDto;
import pl.com.bottega.erp.sales.presentation.ClientOrderListItemDto;
import pl.com.bottega.erp.sales.presentation.OrderFinder;

@Finder
public class JpaOrderFinder implements OrderFinder {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ClientOrderDetailsDto getDetails(Long orderId) {
        Order order = entityManager.find(Order.class, orderId);
        return toDetailsDto(order);
    }

    private ClientOrderDetailsDto toDetailsDto(Order order) {
        ClientOrderDetailsDto dto = new ClientOrderDetailsDto();
        dto.setOrderId(order.getId());
        dto.setTotalCost(order.getTotalCost());
        dto.setSubmitDate(order.getSubmitDate());
        dto.setStatus(order.getStatus());
        dto.setOrderedProducts(order.getOrderedProducts());
        return dto;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<ClientOrderListItemDto> findCurrentClientsOrders() {
        Query query = entityManager
                .createQuery("select new pl.com.bottega.erp.sales.presentation.ClientOrderListItemDto("
                        + "o.id, o.totalCost, o.submitDate, o.status) from Order o");
        return query.getResultList();
    }
}
