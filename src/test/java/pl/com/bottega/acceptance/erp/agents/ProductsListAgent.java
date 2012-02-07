package pl.com.bottega.acceptance.erp.agents;

public interface ProductsListAgent {

    /**
     * checkout products from the basket
     */
    void checkout();
    
    int getBasketItemsCount();

    void addAnyProductToBasket();

    /**
     * @return true if no products are available
     */
    boolean productsExist();
}
