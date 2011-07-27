package pl.com.bottega.erp.sales.presentation;

import java.util.List;

public interface OrderFinder {

    List<ClientOrderListItemDto> findCurrentClientsOrders();

    ClientOrderDetailsDto getDetails(Long orderId);
}
