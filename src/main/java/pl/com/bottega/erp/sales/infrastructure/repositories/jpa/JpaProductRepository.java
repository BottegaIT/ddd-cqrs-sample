package pl.com.bottega.erp.sales.infrastructure.repositories.jpa;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.com.bottega.ddd.domain.annotations.DomainRepositoryImpl;
import pl.com.bottega.erp.sales.domain.Product;
import pl.com.bottega.erp.sales.domain.ProductRepository;

@DomainRepositoryImpl
@Stateless
public class JpaProductRepository implements ProductRepository {

	@PersistenceContext
    protected EntityManager entityManager;
	
	@Override
	public Product load(Long id) {
		return entityManager.find(Product.class, id);
	}
}
