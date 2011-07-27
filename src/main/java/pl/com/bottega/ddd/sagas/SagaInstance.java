package pl.com.bottega.ddd.sagas;

/**
 * 
 * @author Rafał Jamróz
 * 
 * @param <M>
 *            data type (memento)
 */
public class SagaInstance<M> {
    protected M data;
    private boolean completed;

    public M getData() {
        return data;
    }

    public void setData(M data) {
        this.data = data;
    }

    protected void markAsCompleted() {
        completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }
}
