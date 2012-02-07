package pl.com.bottega.erp.sales.domain;

import pl.com.bottega.ddd.domain.annotations.DomainRepository;

@DomainRepository
public interface ClientRepository {

    Client load(Long clientId);

}
