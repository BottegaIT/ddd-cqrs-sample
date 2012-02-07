/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.com.bottega.erp.sales;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.junit.Assert.assertEquals;

import java.util.Collections;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.erp.sales.application.commands.AddProductToOrderCommand;
import pl.com.bottega.erp.sales.application.commands.CreateOrderCommand;
import pl.com.bottega.erp.sales.application.commands.SubmitOrderCommand;
import pl.com.bottega.erp.sales.domain.Order.OrderStatus;
import pl.com.bottega.erp.sales.domain.OrderedProduct;
import pl.com.bottega.erp.sales.domain.events.OrderCreatedEvent;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;
import pl.com.bottega.erp.sales.presentation.ClientOrderDetailsDto;
import pl.com.bottega.erp.sales.presentation.OrderFinder;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;

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
        assertEquals(OrderStatus.SUBMITTED, orderFinder.getClientOrderDetails(orderId).getOrderStatus());
        events.expect(new OrderCreatedEvent(orderId), new OrderSubmittedEvent(orderId));
    }
    
    @Test
    public void shouldAddAlreadyAddedProdcyByIncreasingQuantity(){
    	//given
    	Long existingProductId = anyProduct().getProductId();
    	//when
    	Long orderId = createOrderWithProduct(existingProductId);
    	addProduct(existingProductId, orderId);
    	//then
    	 assertEquals(1, orderFinder.getClientOrderDetails(orderId).getOrderedProducts().size());
    }
    
    @Test
    public void shouldAddProdct(){
    	//given
    	Long existingProductId = anyProduct().getProductId();
    	Long otherProductId = anyOtherProduct().getProductId();
    	//when
    	Long orderId = createOrderWithProduct(existingProductId);
    	addProduct(otherProductId, orderId);
    	//then
    	 assertEquals(2, orderFinder.getClientOrderDetails(orderId).getOrderedProducts().size());
    }

  

	// helper methods
    // given
    private ProductListItemDto anyProduct() {
        return productFinder.findProducts(new ProductSearchCriteria()).getItems().get(0);
    }
    private ProductListItemDto anyOtherProduct() {
        return productFinder.findProducts(new ProductSearchCriteria()).getItems().get(1);
    }

    // when
    private Long createOrderWithProduct(Long productId) {
        CreateOrderCommand command = new CreateOrderCommand(Collections.singletonMap(productId, 1));
        return (Long) gate.dispatch(command);
    }

    private void submitOrder(Long orderId) {
        gate.dispatch(new SubmitOrderCommand(orderId));
    }
    
    private void addProduct(Long productId, Long orderId) {
		AddProductToOrderCommand command = new AddProductToOrderCommand(orderId, productId, 1);
		gate.dispatch(command);
	}

    // then
    private void assertUnconfirmedOrderExistsWithProduct(Long orderId, Long productId) {
        ClientOrderDetailsDto order = orderFinder.getClientOrderDetails(orderId);
        OrderedProduct onlyProduct = getOnlyElement(order.getOrderedProducts());
        assertEquals(OrderStatus.DRAFT, order.getOrderStatus());
        assertEquals(productId, onlyProduct.getProductId());
    }
}
