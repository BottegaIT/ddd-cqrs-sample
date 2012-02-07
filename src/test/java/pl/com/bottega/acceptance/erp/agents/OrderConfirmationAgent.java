package pl.com.bottega.acceptance.erp.agents;

public interface OrderConfirmationAgent {

    int getProductsCount();

    void submit();

    boolean isSubmitted();
}
