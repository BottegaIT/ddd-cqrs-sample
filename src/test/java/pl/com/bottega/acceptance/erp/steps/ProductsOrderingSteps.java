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
