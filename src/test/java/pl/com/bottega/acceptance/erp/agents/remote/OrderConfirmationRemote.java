package pl.com.bottega.acceptance.erp.agents.remote;

import javax.inject.Inject;

import pl.com.bottega.acceptance.commons.RemoteAgent;
import pl.com.bottega.acceptance.erp.agents.OrderConfirmationAgent;

/**
 * @author Rafał Jamróz
 */
@RemoteAgent
public class OrderConfirmationRemote implements OrderConfirmationAgent {

    /**
     * Order in the scenario context.
     * 
     * @see ProductsListRemote#currentOrder
     */
    @Inject
    private CurrentOrder currentOder;

    @Override
    public int getProductsCount() {
        // return finder.getOrder(currentOrder.getId()).getProductsCount();
        return 1;
    }

    @Override
    public void submit() {
        // send(new SubmitOrderCommand(currentOrder.getId())
    }

    @Override
    public boolean isSubmitted() {
        // return finder.getOrder(currentOrder.getId()).isConfirmed();
        return true;
    }
}
