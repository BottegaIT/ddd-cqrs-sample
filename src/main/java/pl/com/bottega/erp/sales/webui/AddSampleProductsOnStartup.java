package pl.com.bottega.erp.sales.webui;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import pl.com.bottega.ddd.domain.sharedcernel.Money;
import pl.com.bottega.erp.sales.domain.Client;
import pl.com.bottega.erp.sales.domain.Product;
import pl.com.bottega.erp.sales.domain.Product.ProductType;

/**
 * @deprecated development only
 */
@Component
public class AddSampleProductsOnStartup {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private PlatformTransactionManager transactionManager;

    @PostConstruct
    public void addSampleProductsToRepo() {
        TransactionStatus tx = transactionManager.getTransaction(new DefaultTransactionDefinition());
        em.persist(product("papier toaletowy", 0.77));
        em.persist(food("turbosprezarka", 13.37));
        em.persist(drug("pompa do betonu", 4.31));
        em.persist(product("wrota swiatow", 665.99));
        em.persist(new Client());
        transactionManager.commit(tx);
    }

    private Product food(String name, double cost) {
        return new Product(ProductType.FOOD, new Money(new BigDecimal(cost)), name);
    }

    private Product drug(String name, double cost) {
        return new Product(ProductType.DRUG, new Money(new BigDecimal(cost)), name);
    }

    private Product product(String name, double cost) {
        return new Product(ProductType.STANDARD, new Money(new BigDecimal(cost)), name);
    }
}
