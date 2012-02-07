package pl.com.bottega.ddd.sagas;

/**
 * 
 * @author Rafał Jamróz
 * 
 * @param <D>
 *            saga data type (memento)
 */
public class SagaInstance<D> {
    protected D data;
    private boolean completed;

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    protected void markAsCompleted() {
        completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }
}
