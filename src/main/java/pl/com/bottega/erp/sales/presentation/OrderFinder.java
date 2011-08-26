package pl.com.bottega.erp.sales.presentation;

import java.util.List;

public interface OrderFinder {
    /**
     * Method for orders manager.
     */
    List<ClientOrderListItemDto> findOrders();

    /**
     * Method for customers that want to find their unconfirmed own orders.
     */
    UnconfirmedOrderDetailsDto getUnconfirmedOrderDetails(Long orderId);

    /**
     * Method for privileged users.
     */
    OrderDetailsDto getOrderDetails(Long orderId);
}
