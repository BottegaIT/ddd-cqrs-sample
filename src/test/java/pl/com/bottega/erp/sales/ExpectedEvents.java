package pl.com.bottega.erp.sales;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.stereotype.Component;

import pl.com.bottega.ddd.domain.DomainEvent;
import pl.com.bottega.ddd.infrastructure.events.impl.SimpleEventPublisher;
import pl.com.bottega.ddd.infrastructure.events.impl.handlers.EventHandler;

@Component
public class ExpectedEvents implements TestRule {

    @Inject
    private SimpleEventPublisher eventPublisher;

    protected List<Object> publishedEvents;
    protected List<Object> expectedEvents;

    @PostConstruct
    public void initialize() {
        eventPublisher.registerEventHandler(new MyEventHandler());
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new MyStatement(base);
    }

    /**
     * Expects specified events in any order. Passes if other events are found
     * as well.
     */
    public void expect(DomainEvent... events) {
        expectedEvents.addAll(asList(events));
    }

    private class MyEventHandler implements EventHandler {

        @Override
        public boolean canHandle(Object event) {
            return true;
        }

        @Override
        public void handle(Object event) {
            publishedEvents.add(event);
        }
    }

    private class MyStatement extends Statement {

        private final Statement statement;

        public MyStatement(Statement statement) {
            this.statement = statement;
        }

        @Override
        public void evaluate() throws Throwable {
            publishedEvents = new ArrayList<Object>();
            expectedEvents = new ArrayList<Object>();
            statement.evaluate();
            assertTrue("Events published: " + publishedEvents + ", events expected:" + expectedEvents,
                    publishedEvents.containsAll(expectedEvents));
        }
    }
}
