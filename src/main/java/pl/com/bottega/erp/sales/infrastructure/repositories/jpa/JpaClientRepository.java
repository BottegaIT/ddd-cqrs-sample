package pl.com.bottega.erp.sales.infrastructure.repositories.jpa;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.com.bottega.ddd.domain.annotations.DomainRepositoryImpl;
import pl.com.bottega.erp.sales.domain.Client;
import pl.com.bottega.erp.sales.domain.ClientRepository;

@DomainRepositoryImpl
public class JpaClientRepository implements ClientRepository {

	@PersistenceContext(unitName="defaultPU")
    protected EntityManager entityManager;


    public Client load(Long id) {
        return entityManager.find(Client.class, id);
    }

    public void delete(Long id) {
        entityManager.remove(load(id));
    }

    public void persist(Client entity) {
        entityManager.persist(entity);
    }

    public Client save(Client entity) {
        return entityManager.merge(entity);
    }
	
}
