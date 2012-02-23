package pl.com.bottega.erp.sales.webui;

import java.math.BigDecimal;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.com.bottega.ddd.domain.sharedkernel.Money;
import pl.com.bottega.erp.sales.domain.Client;
import pl.com.bottega.erp.sales.domain.Product;
import pl.com.bottega.erp.sales.domain.Product.ProductType;

/**
 * @deprecated development only
 */
@Singleton // I wish we could use @Startup and @PostConstruct but somehow this does not go along with OpenJPA initialization
public class AddSampleProductsOnStartup {

	private boolean initialized = false;
	
	@PersistenceContext
    private EntityManager em;

	public void run()
	{
		
	}

    public void addSampleProductsToRepo() {
    	
    	if (initialized)
    	{
    		return;
    	}
        for (int i = 1; i < 21; i++) {
            em.persist(product(String.format("Electronic Gizmo %02d", i), 0.99));
            em.persist(product(String.format("Cell Phone with 32GB flash memory %02d", i), 299.99));
            em.persist(food(String.format("Software Engineering Audiobook %02d", i), 17.50));
            em.persist(drug(String.format("PC Game including Zombies Part %02d", i), 39.89));
            em.persist(product(String.format("Tablet with Keyboard %02d", i), 459.99));
        }
        em.persist(new Client());
        initialized = true;
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
