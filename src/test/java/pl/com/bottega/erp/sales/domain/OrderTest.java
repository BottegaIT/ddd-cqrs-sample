package pl.com.bottega.erp.sales.domain;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

import pl.com.bottega.ddd.domain.DomainEvent;
import pl.com.bottega.ddd.domain.DomainEventPublisher;
import pl.com.bottega.ddd.domain.sharedkernel.Money;
import pl.com.bottega.erp.sales.domain.Order.OrderStatus;
import pl.com.bottega.erp.sales.domain.Product.ProductType;
import pl.com.bottega.erp.sales.domain.errors.OrderOperationException;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;

public class OrderTest {
    /**
     * Order under test.
     */
    private Order order;

    private DomainEventPublisher eventPublisherMock;
    private RebatePolicy rebatePolicyMock;

    @Before
    public void beforeEachMethod() throws Exception {
        rebatePolicyMock = mock(RebatePolicy.class);
        eventPublisherMock = mock(DomainEventPublisher.class);
        applyRebate(Money.ZERO);
        order = new Order(new Client(), Money.ZERO, OrderStatus.DRAFT);
        order.setRebatePolicy(rebatePolicyMock);
        order.setEventPubslisher(eventPublisherMock);
    }

    @Test
    public void shouldBeDraftWhenCreated() throws Exception {
        assertEquals(OrderStatus.DRAFT, order.getStatus());
    }

    @Test
    public void shouldSubmitOrderWithProduct() throws Exception {
        // when
        order.addProduct(anyProduct(), 1);
        order.submit();
        // then
        expectEvent(OrderSubmittedEvent.class);
    }

    @Test
    public void shouldCountProductsNumberCorrectly() throws Exception {
        // given
        Product product = anyProduct();
        // when
        order.addProduct(product, 1);
        order.addProduct(product, 1);
        // then
        OrderedProduct ordered = getOnlyElement(order.getOrderedProducts());
        assertEquals(product.getName(), ordered.getName());
        assertEquals(2, ordered.getQuantity());
    }

    @Test
    public void shouldCountTotalCostWithoutRabate() throws Exception {
        // when
        order.addProduct(productCosting(1.13, "PLN"), 1);
        order.addProduct(productCosting(2.15, "PLN"), 2);
        // then
        assertEquals(money(5.43, "PLN"), order.getTotalCost());
    }

    @Test
    public void shouldCountTotalCostWithRabate() throws Exception {
        // given
        applyRebate(money(0.15, "PLN"));
        // when
        order.addProduct(productCosting(1.10, "PLN"), 1);
        order.addProduct(productCosting(2.20, "PLN"), 1);
        // then
        assertEquals(money(3.00, "PLN"), order.getTotalCost());
    }

    @Test
    public void cantAddProductsAfterSubmitting() throws Exception {
        // given
        order.addProduct(anyProduct(), 1);
        order.submit();
        try {
            // when
            order.addProduct(anyProduct(), 1);
            fail("exception should haven been thrown");
        } catch (OrderOperationException e) {
            // then - as we expected
        }
    }

    private Product anyProduct() {
        final double price = 3.14;
        final String currencyCode = "EUR";
        return productCosting(price, currencyCode);
    }

    private Product productCosting(double price, String currencyCode) {
        final ProductType type = ProductType.FOOD;
        final String name = "foo";
        return product(type, price, currencyCode, name);
    }

    private Product product(ProductType type, double price, String currencyCode, String name) {
        return new Product(type, new Money(price, Currency.getInstance(currencyCode)), name);
    }

    private void expectEvent(Class<? extends DomainEvent> eventClass) {
        verify(eventPublisherMock).publish(any(eventClass));
    }

    private Money money(double value, String currencyCode) {
        return new Money(value, Currency.getInstance(currencyCode));
    }

    private void applyRebate(Money rebate) {
        given(rebatePolicyMock.calculateRebate(any(Product.class), anyInt(), any(Money.class))).willReturn(rebate);
    }
}
