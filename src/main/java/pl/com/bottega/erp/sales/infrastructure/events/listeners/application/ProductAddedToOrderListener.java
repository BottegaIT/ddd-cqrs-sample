/**
 * 
 */
package pl.com.bottega.erp.sales.infrastructure.events.listeners.application;

import javax.inject.Named;

import pl.com.bottega.ddd.infrastructure.events.EventListener;
import pl.com.bottega.ddd.infrastructure.events.EventListeners;
import pl.com.bottega.erp.sales.application.events.ProductAddedToOrderEvent;

/**
 * @author Slawek
 *
 */
@Named
@EventListeners
public class ProductAddedToOrderListener{

	@EventListener(asynchronous=true)
	public void handle(ProductAddedToOrderEvent event) {
		System.out.println("Spy report:: client: " + event.getClientId() + " have added product: " + event.getProductid());
		
	}
}
