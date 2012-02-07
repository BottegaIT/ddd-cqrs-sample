package pl.com.bottega.ddd.infrastructure.sagas;

public interface SagaEngine {

    void handleSagasEvent(Object event);
}
