package pl.com.bottega.ddd.sagas;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

@Component
public class SimpleSagaLoader implements SagaLoader<SimpleSaga> {

    /**
     * TODO chagne to entity manager
     */
    @PersistenceContext
    private EntityManager entityManager;

    @LoadSaga
    public SimpleSagaData load(SampleDomainEvent event) {
        return loadByAggregateId(event.getAggregateId());
    }

    @LoadSaga
    public SimpleSagaData load(AnotherDomainEvent event) {
        return loadByAggregateId(event.getAggregateId());
    }

    private SimpleSagaData loadByAggregateId(Long orderId) {
        try {
            return getByAggregateId(orderId);
        } catch (NoResultException e) {
            SimpleSagaData simpleSagaData = new SimpleSagaData();
            saveNewSagaData(simpleSagaData);
            return simpleSagaData;
        }
    }

    private SimpleSagaData getByAggregateId(Long aggregateId) {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SimpleSagaData> criteria = qb.createQuery(SimpleSagaData.class);
        Root<SimpleSagaData> data = criteria.from(SimpleSagaData.class);
        criteria.where(qb.equal(data.get(SimpleSagaData.AGGREGATE_ID), aggregateId));
        TypedQuery<SimpleSagaData> query = entityManager.createQuery(criteria);
        query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        return query.getSingleResult();
    }

    private void saveNewSagaData(SimpleSagaData simpleSagaData) {
        entityManager.persist(simpleSagaData);
    }
}
