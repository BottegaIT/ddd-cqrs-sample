package pl.com.bottega.erp.sales.domain;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.com.bottega.ddd.domain.BaseEntity.EntityStatus;
import pl.com.bottega.ddd.domain.annotations.DomainFactory;
import pl.com.bottega.ddd.domain.sharedkernel.Money;
import pl.com.bottega.ddd.domain.support.InjectorHelper;
import pl.com.bottega.erp.sales.domain.Order.OrderStatus;
import pl.com.bottega.erp.sales.domain.errors.OrderCreationException;

/**
 * Sample factory that assembles complex Aggregate.<br>
 * <br>
 * Factory is framework managed component because it need a Repository injection
 * in order to be able to check gratis and policy parameters
 * 
 * @author Slawek
 * 
 */
@DomainFactory
public class OrderFactory {

    @Inject
    private RebatePolicyFactory rebatePolicyFactory;
    @Inject
    private InjectorHelper injector;

    public Order crateOrder(Client client) throws OrderCreationException {
        checkIfclientCanPerformPurchase(client);

        Order order = new Order(client, Money.ZERO, OrderStatus.DRAFT);
        injector.injectDependencies(order);

        RebatePolicy rebatePolicy = rebatePolicyFactory.createRebatePolicy();
        order.setRebatePolicy(rebatePolicy);

        addGratis(order, client);

        return order;
    }

    private void checkIfclientCanPerformPurchase(Client client) throws OrderCreationException {
        if (client.getEntityStatus() != EntityStatus.ACTIVE)
            throw new OrderCreationException("Can not perform purchase, because of the Client status: "
                    + client.getEntityStatus(), client.getEntityId());
    }

    /**
     * TODO introduce Domain Class: GatisProducts that contains product and
     * quantity
     * 
     * @param order
     * @param client
     */
    private void addGratis(Order order, Client client) {
        List<Product> gratisProducts = loadGratis(client);

        for (Product product : gratisProducts) {
            order.addProduct(product, 1);
        }
    }

    // TODO load gratis form GratsService/Repo
    private List<Product> loadGratis(Client client) {
        return new ArrayList<Product>(0);
    }

}
