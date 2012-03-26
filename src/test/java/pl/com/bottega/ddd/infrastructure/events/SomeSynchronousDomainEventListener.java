package pl.com.bottega.ddd.infrastructure.events;

import javax.inject.Named;


@Named
@EventListeners
public class SomeSynchronousDomainEventListener {

	private static int someTestDomainEventCounter = 0;
	
	@EventListener
	public void handle(SomeTestDomainEvent event)
	{
		someTestDomainEventCounter++;
	}
	
	public boolean hasHandledNumberOfEvents(int number)
	{
		return someTestDomainEventCounter == number;
	}
	
	public void reset()
	{
		someTestDomainEventCounter = 0;
	}
}
