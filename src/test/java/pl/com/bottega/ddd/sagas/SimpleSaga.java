package pl.com.bottega.ddd.sagas;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import pl.com.bottega.erp.sales.domain.events.OrderCreatedEvent;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;

/**
 * TODO LATER saga with multiple aggregates, saga with injected current user.
 */
@Saga
public class SimpleSaga extends SagaInstance<SimpleSagaData> {

    @Inject
    private SagaSpy spy;

    @SagaAction
    public void onOrderCreated(OrderCreatedEvent event) {
        data.setCreatedOrderId(event.getOrderId());
        completeIfPossible();
    }

    @SagaAction
    public void onOrderSubmited(OrderSubmittedEvent event) {
        data.setSubmittedOrderId(event.getOrderId());
        completeIfPossible();
    }

    private void completeIfPossible() {
        if (data.getCreatedOrderId() != null && data.getSubmittedOrderId() != null) {
            assertEquals(data.getCreatedOrderId(), data.getSubmittedOrderId());
            spy.callMethod();
            markAsCompleted();
        }
    }
}
