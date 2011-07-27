package pl.com.bottega.ddd.sagas;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import pl.com.bottega.erp.sales.domain.events.OrderCreatedEvent;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;

@Component
public class SimpleSagaLoader implements SagaLoader<SimpleSaga> {

    private Set<SimpleSagaData> data = new HashSet<SimpleSagaData>();

    /**
     * TODO change return type to SimpleSagaData(?)
     */
    @LoadSaga
    public SimpleSagaData load(OrderCreatedEvent event) {
        return loadByOrderId(event.getOrderId());
    }

    @LoadSaga
    public SimpleSagaData load(OrderSubmittedEvent event) {
        return loadByOrderId(event.getOrderId());
    }

    private SimpleSagaData loadByOrderId(Long orderId) {
        SimpleSagaData simpleSagaData = findByOrderId(orderId);
        if (simpleSagaData == null) {
            simpleSagaData = new SimpleSagaData();
            data.add(simpleSagaData);
        }
        return simpleSagaData;
    }

    private SimpleSagaData findByOrderId(Long orderId) {
        for (SimpleSagaData sagaData : data) {
            if (orderId.equals(sagaData.getCreatedOrderId())) {
                return sagaData;
            }
        }
        return null;
    }

}
