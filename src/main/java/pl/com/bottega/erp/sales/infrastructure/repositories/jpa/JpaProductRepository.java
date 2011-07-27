package pl.com.bottega.erp.sales.infrastructure.repositories.jpa;

import pl.com.bottega.ddd.domain.annotations.DomainRepositoryImpl;
import pl.com.bottega.ddd.infrastructure.repo.jpa.GenericJpaRepositoryForBaseEntity;
import pl.com.bottega.erp.sales.domain.Product;
import pl.com.bottega.erp.sales.domain.ProductRepository;

@DomainRepositoryImpl
public class JpaProductRepository extends GenericJpaRepositoryForBaseEntity<Product> implements ProductRepository {
}
