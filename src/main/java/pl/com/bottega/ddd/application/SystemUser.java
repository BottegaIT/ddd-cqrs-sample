/**
 * 
 */
package pl.com.bottega.ddd.application;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.com.bottega.ddd.application.annotation.ApplicationStatefullComponent;
import pl.com.bottega.erp.sales.domain.Client;

/**
 * Mock implmentation of currenly authenticated user.
 * 
 * @author Slawek
 */
@SuppressWarnings("serial")
@ApplicationStatefullComponent
public class SystemUser implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @return any user id
     */
    @SuppressWarnings("unchecked")
    public Long getUserId() {
        // return userId;
        List<Client> clients = entityManager.createQuery("from Client").getResultList();
        return clients.get(0).getId();
    }
}