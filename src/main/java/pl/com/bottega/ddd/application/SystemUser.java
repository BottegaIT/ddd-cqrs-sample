/**
 * 
 */
package pl.com.bottega.ddd.application;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.com.bottega.erp.sales.domain.Client;

/**
 * Mock implmentation of currenly authenticated user.
 * 
 * @author Slawek
 */
@Stateful
public class SystemUser {

    @PersistenceContext(unitName="defaultPU")
    private EntityManager entityManager;

    /**
     * @return any user id
     */
    @SuppressWarnings("unchecked")
    public Long getUserId() {
        // return userId;
        List<Client> clients = entityManager.createQuery("select c from Client c").getResultList();
        return clients.get(0).getEntityId();
    }
}