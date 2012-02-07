package pl.com.bottega.acceptance.erp.agents.remote;

import javax.inject.Inject;

import pl.com.bottega.acceptance.commons.RemoteAgent;
import pl.com.bottega.acceptance.erp.agents.OrderConfirmationAgent;
import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.erp.sales.application.commands.SubmitOrderCommand;
import pl.com.bottega.erp.sales.domain.Order.OrderStatus;
import pl.com.bottega.erp.sales.presentation.OrderFinder;

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
    private CurrentOrder currentOrder;
    @Inject
    private Gate gate;
    @Inject
    private OrderFinder orderFinder;

    @Override
    public int getProductsCount() {
        return orderFinder.getClientOrderDetails(currentOrder.getOrderId()).getOrderedProducts().size();
    }

    @Override
    public void submit() {
        gate.dispatch(new SubmitOrderCommand(currentOrder.getOrderId()));
    }

    @Override
    public boolean isSubmitted() {
        OrderStatus orderStatus = orderFinder.getClientOrderDetails(currentOrder.getOrderId()).getOrderStatus();
        return OrderStatus.SUBMITTED.equals(orderStatus);
    }
}
