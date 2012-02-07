package pl.com.bottega.erp.sales.saga;

import javax.inject.Inject;

import pl.com.bottega.ddd.sagas.Saga;
import pl.com.bottega.ddd.sagas.SagaAction;
import pl.com.bottega.ddd.sagas.SagaInstance;
import pl.com.bottega.erp.sales.domain.Order;
import pl.com.bottega.erp.sales.domain.OrderRepository;
import pl.com.bottega.erp.sales.domain.events.OrderCreatedEvent;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;
import pl.com.bottega.erp.shipping.domain.events.OrderShippedEvent;
import pl.com.bottega.erp.shipping.domain.events.ShipmentDeliveredEvent;

@Saga
public class OrderShipmentStatusTrackerSaga extends SagaInstance<OrderShipmentStatusTrackerData> {

    @Inject
    private OrderRepository orderRepository;

    @SagaAction
    public void handleOrderCreated(OrderCreatedEvent event) {
        data.setOrderId(event.getOrderId());
        completeIfPossible();
    }

    @SagaAction
    public void handleOrderSubmitted(OrderSubmittedEvent event) {
        data.setOrderId(event.getOrderId());
        // do some business
        completeIfPossible();
    }

    @SagaAction
    public void orderShipped(OrderShippedEvent event) {
        data.setOrderId(event.getOrderId());
        data.setShipmentId(event.getShipmentId());
        completeIfPossible();
    }

    @SagaAction
    public void shipmentDelivered(ShipmentDeliveredEvent event) {
        data.setShipmentId(event.getShipmentId());
        data.setShipmentReceived(true);
        completeIfPossible();
    }

    private void completeIfPossible() {
        if (data.getOrderId() != null && data.getShipmentId() != null && data.getShipmentReceived()) {
            Order shippedOrder = orderRepository.load(data.getOrderId());
            shippedOrder.archive();
            orderRepository.save(shippedOrder);
            markAsCompleted();
        }
    }
}
