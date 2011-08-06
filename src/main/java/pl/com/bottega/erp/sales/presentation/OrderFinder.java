package pl.com.bottega.erp.sales.presentation;

import java.util.List;

public interface OrderFinder {
    /**
     * @deprecated Method for ???
     */
    List<ClientOrderListItemDto> findCurrentClientsOrders();

    /**
     * Method for customers that want to find their own orders.
     */
    UnconfirmedOrderDetailsDto getUnconfirmedOrderDetails(Long orderId);

    /**
     * Method for privileged users.
     */
    OrderDetailsDto getOrderDetails(Long orderId);
}
