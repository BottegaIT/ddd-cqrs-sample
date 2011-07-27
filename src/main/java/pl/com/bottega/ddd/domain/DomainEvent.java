package pl.com.bottega.ddd.domain;

import java.io.Serializable;

/**
 * 
 * @author Slawek
 * 
 */
//TODO: zastanowic sie czy potrzebne? agregateId nie jest nigdy uzywany polimorficznie
@SuppressWarnings("serial")
public class DomainEvent<ID extends Serializable> implements Serializable {
    private final ID aggregateId;

    public DomainEvent(ID aggregateId) {
        this.aggregateId = aggregateId;
    }

    public ID getAggregateId() {
        return aggregateId;
    }
}
