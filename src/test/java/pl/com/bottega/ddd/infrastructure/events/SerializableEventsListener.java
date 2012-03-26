package pl.com.bottega.ddd.infrastructure.events;

import java.io.Serializable;

import javax.inject.Named;


@Named
@EventListeners
public class SerializableEventsListener {

	private static int handledEventCounter = 0;
	
	@EventListener
	public void handle(Serializable applicationEvent)
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
