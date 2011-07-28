package pl.com.bottega.ddd.sagas;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.com.bottega.ddd.domain.DomainEventPublisher;

/**
 * TODO LATER saga with multiple aggregates, saga with injected current user.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/sagasIntegrationTestContext.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
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
        publisher.publish(new SampleDomainEvent(1L));
        publisher.publish(new AnotherDomainEvent(1L, "data"));
        // then
        assertEquals(1, spy.getSampleEventHandledCount());
        assertEquals(1, spy.getAnotherEventHandledCount());
        assertEquals(1, spy.getSagaCompletedCount());
    }

    @Test
    public void shouldNotCompleteSameSagaTwice() throws Exception {
        // when
        publisher.publish(new SampleDomainEvent(1L));
        publisher.publish(new AnotherDomainEvent(1L, "data"));
        publisher.publish(new SampleDomainEvent(1L));
        // then
        assertEquals(2, spy.getSampleEventHandledCount());
        assertEquals(1, spy.getAnotherEventHandledCount());
        assertEquals(1, spy.getSagaCompletedCount());
    }
}
