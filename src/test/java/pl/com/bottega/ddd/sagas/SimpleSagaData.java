package pl.com.bottega.ddd.sagas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SimpleSagaData {

    public static final String AGGREGATE_ID = "aggregateId";

    @Id
    @GeneratedValue
    private Long id;

    private Long aggregateId;

    private String data;

    public Long getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(Long aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
