/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.com.bottega.erp.sales;

import static java.util.Arrays.asList;
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

/**
 * JUnit test rule for convenient asserting expected events. Rule will track
 * which events were published during the test method execution. Validating if
 * the expected events occurred will happen after the whole method executes
 * correctly.
 * 
 * @author Rafał Jamróz
 */
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
     * Expects specified events in any order. (Also passes if other events were
     * published)
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
