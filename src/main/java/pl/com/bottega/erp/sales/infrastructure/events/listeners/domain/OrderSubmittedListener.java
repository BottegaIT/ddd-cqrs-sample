/**
 * 
 */
package pl.com.bottega.erp.sales.infrastructure.events.listeners.domain;

import javax.inject.Named;

import pl.com.bottega.ddd.infrastructure.events.EventListener;
import pl.com.bottega.ddd.infrastructure.events.EventListeners;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;

/**
 * @author Slawek
 *
 */
@Named
@EventListeners
public class OrderSubmittedListener {

	@EventListener(asynchronous=true)
	public void handle(OrderSubmittedEvent event) {
		System.out.println("Sending mail aboud order: " + event.getOrderId());	
	}
}
