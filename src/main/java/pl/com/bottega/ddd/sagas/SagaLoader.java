package pl.com.bottega.ddd.sagas;

/**
 * @author Rafał Jamróz
 * 
 * @param <T>
 *            saga type
 * @param <D>
 *            saga data type
 */
public interface SagaLoader<T extends SagaInstance<D>, D> {

    void removeSaga(T saga);

    D createNewSagaData();
}
