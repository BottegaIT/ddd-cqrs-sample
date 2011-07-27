/**
 * 
 */
package pl.com.bottega.erp.sales.infrastructure.events.listeners.domain;

import pl.com.bottega.ddd.infrastructure.events.EventListener;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;

/**
 * @author Slawek
 *
 */
public class OrderSubmittedListener {

	@EventListener
	public void handle(OrderSubmittedEvent event) {
		System.out.println("Sending mail aboud order: " + event.getOrderId());
		
	}

}
