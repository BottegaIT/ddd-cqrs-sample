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
 * @author Slawek
 *
 */
@SuppressWarnings("serial")
@ApplicationStatefullComponent
public class SystemUser implements Serializable{

	/**
	 * ID of the JPA entity
	 */
	private Long userId;
	
	@PersistenceContext
    private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public Long getUserId() {
		//return userId;
		List<Client> clients = entityManager.createQuery("from Client").getResultList();
	    return clients.get(0).getId();
	}
	
	//TODO: to be called from spring security
	public void authenticate(Long userId){
		this.userId = userId;
	}
	

	

 
}
