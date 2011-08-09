package pl.com.bottega.erp.sagas;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import pl.com.bottega.ddd.sagas.LoadSaga;
import pl.com.bottega.ddd.sagas.SagaManager;
import pl.com.bottega.erp.sales.domain.events.OrderCreatedEvent;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;
import pl.com.bottega.erp.shipping.domain.events.OrderShippedEvent;
import pl.com.bottega.erp.shipping.domain.events.ShipmentDeliveredEvent;

@Component
public class OrderShipmentStatusTrackerSagaManager implements
        SagaManager<OrderShipmentStatusTrackerSaga, OrderShipmentStatusTrackerData> {

    @PersistenceContext
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
        Query query = entityManager.createQuery("from OrderShipmentStatusTrackerData where orderId=:orderId")
                .setParameter("orderId", orderId);
        return (OrderShipmentStatusTrackerData) query.getSingleResult();
    }

    private OrderShipmentStatusTrackerData findByShipmentId(Long shipmentId) {
        Query query = entityManager.createQuery("from OrderShipmentStatusTrackerData where shipmentId=:shipmentId")
                .setParameter("shipmentId", shipmentId);
        return (OrderShipmentStatusTrackerData) query.getSingleResult();
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
