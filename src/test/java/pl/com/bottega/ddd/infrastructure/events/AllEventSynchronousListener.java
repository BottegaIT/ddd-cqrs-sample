package pl.com.bottega.ddd.infrastructure.events;

import javax.inject.Named;

import pl.com.bottega.ddd.domain.DomainEvent;


@Named
@EventListeners
public class AllEventSynchronousListener {

	private static int handledEventCounter = 0;
	
	@EventListener
	public void handle(Object event)
	{
		handledEventCounter++;
	}
	
	
	public boolean hasHandledNumberOfEvents(int number)
	{
		return handledEventCounter == number;
	}
	
	public void reset()
	{
		handledEventCounter = 0;
	}
}
