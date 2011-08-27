package pl.com.bottega.erp.sales.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import pl.com.bottega.ddd.domain.BaseAggregateRoot;
import pl.com.bottega.ddd.domain.annotations.DomainAggregateRoot;
import pl.com.bottega.ddd.domain.sharedcernel.Money;
import pl.com.bottega.erp.sales.domain.errors.OrderOperationException;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;

/**
 * @author Slawek
 */
@Entity
@Table(name = "Orders")
@DomainAggregateRoot
public class Order extends BaseAggregateRoot {

    public enum OrderStatus {
        DRAFT, SUBMITTED, ARCHIVED
    }

    @ManyToOne
    private Client client;

    /**
     * Sample of Value Object usage
     */
    @Embedded
    private Money totalCost;

    /**
     * Sample of encapsulation - this structure is hidden
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderLine> items;

    private Timestamp submitDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    /**
     * Sample of Policy usage (Strategy Design Pattern)<br>
     * Policy is injected by Factory/Repo but can be also changed in business
     * method
     */
    // To be injected by Factory/Repository
    @Transient
    private RebatePolicy rebatePolicy;

    protected Order() {
    }

    /**
     * Meant to be used by factory<br>
     * Notice that Policy is set via setter because Policy need by initialised
     * also in Repository
     */
    Order(Client client, Money initialCost, OrderStatus initialStatus) {
        this.client = client;
        totalCost = initialCost;
        status = initialStatus;

        items = new ArrayList<OrderLine>();
    }

    /**
     * Sample business method that:
     * <ul>
     * <li>hides internal state - OrderLine
     * <li>can veto - if order is not in DRAFT status
     * <li>not only modifies structure (list) but also performs logic -
     * calculates total cost
     * </ul>
     * 
     * @param product
     * @param quantity
     */
    public void addProduct(Product product, int quantity) {
        checkIfDraft();

        OrderLine line = find(product);

        if (line == null) {
            items.add(new OrderLine(product, quantity, rebatePolicy));
        } else {
            line.increaseQuantity(quantity, rebatePolicy);
        }

        recalculate();
    }

    /**
     * Sample business method that uses Policy
     * 
     * @param rebatePolicy
     */
    public void applyRebate(RebatePolicy rebatePolicy) {
        checkIfDraft();

        this.rebatePolicy = rebatePolicy;
        recalculate();
    }

    /**
     * Sample business method
     */
    public void submit() {
        checkIfDraft();

        status = OrderStatus.SUBMITTED;
        submitDate = new Timestamp(System.currentTimeMillis());

        eventPublisher.publish(new OrderSubmittedEvent(getId()));
    }

    private void checkIfDraft() {
        if (status != OrderStatus.DRAFT)
            throw new OrderOperationException("Operation allowed only in DRAFT status", client.getId(), getId());
    }

    /**
     * Sample technique of injecting Policy into the Aggregate.<br>
     * <br>
     * Can be called only once by Factory/Repository<br>
     * 
     * @param rebatePolicy
     */
    public void setRebatePolicy(RebatePolicy rebatePolicy) {
        if (this.rebatePolicy != null)
            throw new IllegalStateException("Policy is already set! Probably You have logical error in code");
        this.rebatePolicy = rebatePolicy;
    }

    private void recalculate() {
        totalCost = Money.ZERO;
        for (OrderLine line : items) {
            line.recalculate(rebatePolicy);
            totalCost = totalCost.add(line.getEffectiveCost());
        }
    }

    private OrderLine find(Product product) {
        for (OrderLine line : items) {
            if (product.equals(line.getProduct()))
                return line;
        }
        return null;
    }

    /**
     * Sample encapsulation of unstable internal implementation - assumption:
     * this impl may vary in time. So we use projection of the internal state of
     * this Aggregate<br>
     * <br>
     * Projection hides internal structure using Value Objects. Projection is
     * also unmodifiable.
     * 
     * @return
     */
    public List<OrderedProduct> getOrderedProducts() {
        List<OrderedProduct> result = new ArrayList<OrderedProduct>(items.size());

        for (OrderLine line : items) {
            result.add(new OrderedProduct(line.getProduct().getId(), line.getProduct().getName(), line.getQuantity(),
                    line.getEffectiveCost(), line.getRegularCost()));
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Sample access to the internal state - <b>remember to allow such access
     * only if makes sense</b>, don't do that by default!<br>
     * <br>
     * Notice that there is no setter!
     * 
     * @return
     */
    public Money getTotalCost() {
        return totalCost;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Client getClient() {
        return client;
    }

    /**
     * @return number of lines
     */
    public int getLinesNumber() {
        return items.size();
    }

    /**
     * @return number of items (single line may contain many items)
     */
    public int getItemsNumber() {
        int number = 0;

        for (OrderLine line : items) {
            number += line.getQuantity();
        }

        return number;
    }

    /**
     * @param product
     * @return
     */
    public boolean contains(Product product) {
        for (OrderLine line : items) {
            if (line.getProduct().equals(product))
                return true;
        }
        return false;
    }

    public void archive() {
        status = OrderStatus.ARCHIVED;
    }
}
