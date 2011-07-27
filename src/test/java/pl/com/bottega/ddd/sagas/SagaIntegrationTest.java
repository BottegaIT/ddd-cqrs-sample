package pl.com.bottega.ddd.sagas;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.com.bottega.ddd.domain.DomainEventPublisher;
import pl.com.bottega.erp.sales.domain.events.OrderCreatedEvent;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SagaIntegrationTest {

    @Inject
    private DomainEventPublisher publisher;

    @Inject
    private SagaSpy spy;

    /**
     * testing {@link SimpleSaga}
     */
    @Test
    public void shouldRunSimpleTwoStepSaga() throws Exception {
        // when
        publisher.publish(new OrderCreatedEvent(1L));
        publisher.publish(new OrderSubmittedEvent(1L));
        // then
        assertTrue(spy.methodHasBeenCalled());
    }
}
