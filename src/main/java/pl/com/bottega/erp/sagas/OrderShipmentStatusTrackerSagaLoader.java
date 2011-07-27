package pl.com.bottega.erp.sagas;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pl.com.bottega.ddd.sagas.LoadSaga;
import pl.com.bottega.ddd.sagas.SagaLoader;
import pl.com.bottega.erp.sales.domain.events.OrderCreatedEvent;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;
import pl.com.bottega.erp.shipping.domain.events.OrderShippedEvent;
import pl.com.bottega.erp.shipping.domain.events.ShipmentDeliveredEvent;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class OrderShipmentStatusTrackerSagaLoader implements SagaLoader<OrderShipmentStatusTrackerSaga> {

    private Set<OrderShipmentStatusTrackerData> data = new HashSet<OrderShipmentStatusTrackerData>();

    @LoadSaga
    public OrderShipmentStatusTrackerData loadSaga(OrderCreatedEvent event) {
        return findByOrderId(event.getOrderId());
    }

    @LoadSaga
    public OrderShipmentStatusTrackerData loadSaga(OrderSubmittedEvent event) {
        return findByOrderId(event.getOrderId());
    }

    @LoadSaga
    public OrderShipmentStatusTrackerData loadSaga(OrderShippedEvent event) {
        return findByOrderId(event.getOrderId());
    }

    @LoadSaga
    public OrderShipmentStatusTrackerData loadSaga(ShipmentDeliveredEvent event) {
        return findByShipmentId(event.getShipmentId());
    }

    private OrderShipmentStatusTrackerData findByOrderId(Long orderId) {
        for (OrderShipmentStatusTrackerData sagaData : data) {
            if (orderId.equals(sagaData.getOrderId())) {
                return sagaData;
            }
        }
        return addNewSagaData();
    }

    private OrderShipmentStatusTrackerData findByShipmentId(Long shipmentId) {
        for (OrderShipmentStatusTrackerData sagaData : data) {
            if (shipmentId.equals(sagaData.getShipmentId())) {
                return sagaData;
            }
        }
        return addNewSagaData();
    }

    private OrderShipmentStatusTrackerData addNewSagaData() {
        OrderShipmentStatusTrackerData sagaData = new OrderShipmentStatusTrackerData();
        data.add(sagaData);
        return sagaData;
    }
}
