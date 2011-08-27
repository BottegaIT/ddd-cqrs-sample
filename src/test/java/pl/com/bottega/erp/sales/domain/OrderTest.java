package pl.com.bottega.erp.sales.domain;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.com.bottega.ddd.domain.DomainEventPublisher;
import pl.com.bottega.ddd.domain.sharedcernel.Money;
import pl.com.bottega.ddd.domain.support.InjectorHelper;
import pl.com.bottega.erp.sales.domain.Order.OrderStatus;
import pl.com.bottega.erp.sales.domain.Product.ProductType;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;
import pl.com.bottega.erp.sales.domain.policies.rebate.StandardRebate;

public class OrderTest {
    /**
     * Order under test.
     */
    private Order order;

    @InjectMocks
    private OrderFactory orderFactory = new OrderFactory();
    @Mock
    private RebatePolicyFactory rebatePolicyFactory;
    @Mock
    private InjectorHelper injector;
    @Mock
    private DomainEventPublisher eventPublisher;

    @Before
    public void beforeEachMethod() throws Exception {
        MockitoAnnotations.initMocks(this);
        given(rebatePolicyFactory.createRebatePolicy()).willReturn(new StandardRebate(0, 0));
        order = orderFactory.crateOrder(new Client());
        order.setEventPubslisher(eventPublisher);
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

    private Product anyProduct() {
        ProductType type = ProductType.FOOD;
        double price = 3.14;
        String currencyCode = "EUR";
        String name = "foo";
        return new Product(type, new Money(price, Currency.getInstance(currencyCode)), name);
    }

    private void expectEvent(Class<OrderSubmittedEvent> eventClass) {
        verify(eventPublisher).publish(any(eventClass));
    }
}
