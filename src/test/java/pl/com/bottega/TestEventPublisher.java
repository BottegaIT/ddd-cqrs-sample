package pl.com.bottega;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import pl.com.bottega.ddd.application.ApplicationEventPublisher;
import pl.com.bottega.ddd.domain.DomainEvent;
import pl.com.bottega.ddd.domain.DomainEventPublisher;

@Singleton
public class TestEventPublisher implements ApplicationEventPublisher,
		DomainEventPublisher {

	protected List<Object> publishedEvents = new ArrayList<Object>();
	protected List<Object> expectedEvents = new ArrayList<Object>();

	public void reset()
	{
		publishedEvents.clear();
	}
	
	@Override
	public void publish(DomainEvent event) {
		publishedEvents.add(event);
	}

	@Override
	public void publish(Serializable applicationEvent) {
		publishedEvents.add(applicationEvent);

	}

	public void expect(DomainEvent... events) {
		expectedEvents.clear();
		expectedEvents.addAll(asList(events));
		assertTrue("Events published: " + publishedEvents
				+ ", events expected:" + expectedEvents,
				publishedEvents.containsAll(expectedEvents));
	}

}
