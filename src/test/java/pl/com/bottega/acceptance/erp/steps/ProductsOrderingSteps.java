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
package pl.com.bottega.acceptance.erp.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import pl.com.bottega.acceptance.commons.Steps;
import pl.com.bottega.acceptance.erp.agents.OrderConfirmationAgent;
import pl.com.bottega.acceptance.erp.agents.ProductsListAgent;

@Steps
public class ProductsOrderingSteps {

    @Inject
    private ProductsListAgent productsList;

    @Inject
    private OrderConfirmationAgent orderConfirmation;

    @Given("Products are available")
    public void productsAreAvailable() {
        // assume there are products
        assertTrue(productsList.productsExist());
    }

    @When("I add any product to basket")
    public void addAnyProductToBasket() {
        productsList.addAnyProductToBasket();
    }

    @Then("Basket should have $number items")
    @Alias("Basket should contain $number item")
    public void shouldBasketHaveItems(int itemsCount) {
        assertEquals(itemsCount, productsList.getBasketItemsCount());
    }

    @When("I checkout")
    public void checkout() {
        productsList.checkout();
    }

    @Then("I should be able to submit order with $count product")
    @Alias("I should be able to submit order with $count products")
    public void shouldBeOnOrderPageWithProducts(int productsCount) throws Exception {
        assertEquals(productsCount, orderConfirmation.getProductsCount());
    }

    @When("I submit the order")
    public void confirmOrder() {
        orderConfirmation.submit();
    }

    @Then("That order should be confirmed")
    public void orderShouldBeConfirmed() {
        assertTrue(orderConfirmation.isSubmitted());
    }
}
