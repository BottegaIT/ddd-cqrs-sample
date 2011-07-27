package pl.com.bottega.ddd.infrastructure.repo.jpa;

import pl.com.bottega.ddd.domain.BaseEntity;

/**
 * 
 * @author Slawek
 *
 * @param <E> JPA Entity Type (DDD: Aggregate, Entity)
 * @param <K> key type
 */
public class GenericJpaRepositoryForBaseEntity<E extends BaseEntity> extends GenericJpaRepository<E, Long>{
		
	public void delete(Long id){
		E entity = load(id);
		entity.markAsRemoved();		
		save(entity);	
	}
}
