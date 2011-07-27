/**
 * 
 */
package pl.com.bottega.erp.sales.application.listeners;

import pl.com.bottega.ddd.infrastructure.events.EventListener;
import pl.com.bottega.ddd.infrastructure.events.EventListeners;
import pl.com.bottega.erp.crm.domain.Customer.CustomerStatus;
import pl.com.bottega.erp.crm.domain.events.CustomerStatusChangedEvent;

/**
 * Sample listener that communicates with other Bounded Context
 * 
 * @author Slawek
 *
 */
@EventListeners
public class CustomerStatusChangedListener{

	@EventListener(aynchronous=true)	
	public void handle(CustomerStatusChangedEvent event) {
		if (event.getStatus() == CustomerStatus.VIP){
			calculateReabteForAllDraftOrders(event.getCustomerId(), 10);
		}		
	}

	/**
	 * @param customerId
	 * @param i
	 */
	private void calculateReabteForAllDraftOrders(Long customerId, int i) {
		// TODO Auto-generated method stub
		
	}

}
