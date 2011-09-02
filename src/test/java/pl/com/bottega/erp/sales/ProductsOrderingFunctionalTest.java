package pl.com.bottega.erp.sales;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collections;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.erp.sales.application.commands.CreateOrderCommand;
import pl.com.bottega.erp.sales.application.commands.SubmitOrderCommand;
import pl.com.bottega.erp.sales.domain.OrderedProduct;
import pl.com.bottega.erp.sales.domain.events.OrderCreatedEvent;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;
import pl.com.bottega.erp.sales.presentation.OrderFinder;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;
import pl.com.bottega.erp.sales.presentation.UnconfirmedOrderDetailsDto;

/**
 * Functional tests for ordering products by clients.
 * 
 * @author Rafał Jamróz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/functionalTestsContext.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductsOrderingFunctionalTest {

    @Inject
    @Rule
    public ExpectedEvents events;

    @Inject
    private ProductFinder productFinder;

    @Inject
    private Gate gate;

    @Inject
    private OrderFinder orderFinder;

    @Test
    public void shouldCreateOrder() throws Exception {
        // given
        Long existingProductId = anyProduct().getProductId();
        // when
        Long unsubmittedOrderId = createOrderWithProduct(existingProductId);
        // then
        events.expect(new OrderCreatedEvent(unsubmittedOrderId));
        assertUnconfirmedOrderExistsWithProduct(unsubmittedOrderId, existingProductId);
    }

    @Test
    public void shouldSubmitCreatedOrder() throws Exception {
        // given
        Long existingProductId = anyProduct().getProductId();
        // when
        Long orderId = createOrderWithProduct(existingProductId);
        submitOrder(orderId);
        // then
        events.expect(new OrderCreatedEvent(orderId), new OrderSubmittedEvent(orderId));
        assertOrderIsConfirmed(orderId);
    }

    // helper methods
    // given
    private ProductListItemDto anyProduct() {
        return productFinder.findProducts(new ProductSearchCriteria()).getItems().get(0);
    }

    // when
    private Long createOrderWithProduct(Long productId) {
        CreateOrderCommand command = new CreateOrderCommand(Collections.singletonMap(productId, 1));
        return (Long) gate.dispatch(command);
    }

    private void submitOrder(Long orderId) {
        gate.dispatch(new SubmitOrderCommand(orderId));
    }

    // then
    private void assertUnconfirmedOrderExistsWithProduct(Long orderId, Long productId) {
        UnconfirmedOrderDetailsDto order = orderFinder.getUnconfirmedOrderDetails(orderId);
        OrderedProduct onlyProduct = getOnlyElement(order.getOrderedProducts());
        assertEquals(productId, onlyProduct.getProductId());
    }

    private void assertOrderIsConfirmed(Long orderId) {
        assertNull(orderFinder.getUnconfirmedOrderDetails(orderId));
    }
}
