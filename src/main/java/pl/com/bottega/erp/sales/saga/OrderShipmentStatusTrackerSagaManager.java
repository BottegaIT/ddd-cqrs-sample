package pl.com.bottega.erp.sales.saga;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.com.bottega.ddd.sagas.LoadSaga;
import pl.com.bottega.ddd.sagas.SagaManager;
import pl.com.bottega.erp.sales.domain.events.OrderCreatedEvent;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;
import pl.com.bottega.erp.shipping.domain.events.OrderShippedEvent;
import pl.com.bottega.erp.shipping.domain.events.ShipmentDeliveredEvent;

@Named
public class OrderShipmentStatusTrackerSagaManager implements
        SagaManager<OrderShipmentStatusTrackerSaga, OrderShipmentStatusTrackerData> {

	@PersistenceContext(unitName="defaultPU")
    private EntityManager entityManager;

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

        throw new UnsupportedOperationException("Not implemented");
    }

    private OrderShipmentStatusTrackerData findByShipmentId(Long shipmentId) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void removeSaga(OrderShipmentStatusTrackerSaga saga) {
        OrderShipmentStatusTrackerData sagaData = entityManager.merge(saga.getData());
        entityManager.remove(sagaData);
    }

    @Override
    public OrderShipmentStatusTrackerData createNewSagaData() {
        OrderShipmentStatusTrackerData sagaData = new OrderShipmentStatusTrackerData();
        entityManager.persist(sagaData);
        return sagaData;
    }
}
