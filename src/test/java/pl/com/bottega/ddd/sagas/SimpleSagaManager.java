package pl.com.bottega.ddd.sagas;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class SimpleSagaManager implements SagaManager<SimpleSaga, SimpleSagaData> {

    private Set<SimpleSagaData> data = new HashSet<SimpleSagaData>();

    @LoadSaga
    public SimpleSagaData load(SampleDomainEvent event) {
        return findByAggregateId(event.getAggregateId());
    }

    @LoadSaga
    public SimpleSagaData load(AnotherDomainEvent event) {
        return findByAggregateId(event.getAggregateId());
    }

    private SimpleSagaData findByAggregateId(Long aggregateId) {
        for (SimpleSagaData sagaData : data) {
            if (aggregateId.equals(sagaData.getAggregateId())) {
                return sagaData;
            }
        }
        return null;
    }

    @Override
    public void removeSaga(SimpleSaga saga) {
        SimpleSagaData foundData = findByAggregateId(saga.getData().getAggregateId());
        data.remove(foundData);
    }

    @Override
    public SimpleSagaData createNewSagaData() {
        SimpleSagaData newSagaData = new SimpleSagaData();
        data.add(newSagaData);
        return newSagaData;
    }

}
