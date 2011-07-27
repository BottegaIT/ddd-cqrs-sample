package pl.com.bottega.erp.sales.infrastructure.repositories.jpa;

import pl.com.bottega.ddd.domain.annotations.DomainRepositoryImpl;
import pl.com.bottega.ddd.infrastructure.repo.jpa.GenericJpaRepository;
import pl.com.bottega.erp.sales.domain.Client;
import pl.com.bottega.erp.sales.domain.ClientRepository;

@DomainRepositoryImpl
public class JpaClientRepository extends GenericJpaRepository<Client, Long> implements ClientRepository {
}
