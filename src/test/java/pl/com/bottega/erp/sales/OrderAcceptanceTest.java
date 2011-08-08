package pl.com.bottega.erp.sales;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collections;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.ddd.domain.DomainEvent;
import pl.com.bottega.erp.sales.application.commands.CreateOrderCommand;
import pl.com.bottega.erp.sales.application.commands.SubmitOrderCommand;
import pl.com.bottega.erp.sales.domain.OrderedProduct;
import pl.com.bottega.erp.sales.domain.events.OrderCreatedEvent;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;
import pl.com.bottega.erp.sales.presentation.OrderFinder;
import pl.com.bottega.erp.sales.presentation.UnconfirmedOrderDetailsDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("blah")
public class OrderAcceptanceTest {

    @Inject
    private Gate gate;

    @Inject
    private OrderFinder orderFinder;

    @Test
    public void shouldOrder() throws Exception {
        // given
        final Long productId = 1L;
        // when

        Long orderId = (Long) gate.dispatch(new CreateOrderCommand(Collections.singletonMap(productId, 1)));
        // then
        expectEvents(new OrderCreatedEvent(orderId));
        UnconfirmedOrderDetailsDto order = orderFinder.getUnconfirmedOrderDetails(orderId);
        OrderedProduct onlyProduct = getOnlyElement(order.getOrderedProducts());
        assertEquals(productId, onlyProduct.getProductId());
    }

    @Test
    public void shouldSubmitCreatedOrder() throws Exception {
        // given
        final Long productId = 1L;
        // when
        Long orderId = (Long) gate.dispatch(new CreateOrderCommand(Collections.singletonMap(productId, 1)));
        gate.dispatch(new SubmitOrderCommand(orderId));
        // then
        expectEvents(new OrderCreatedEvent(orderId), new OrderSubmittedEvent(orderId));
        assertNull(orderFinder.getUnconfirmedOrderDetails(orderId));
    }

    private void expectEvents(DomainEvent... event) {
        // TODO to be implemented
    }
}
