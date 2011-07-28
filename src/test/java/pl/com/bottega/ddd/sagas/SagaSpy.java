package pl.com.bottega.ddd.sagas;

import org.springframework.stereotype.Component;

@Component
public class SagaSpy {

    private int sagaCompletedCount;
    private int anotherEventHandledCount;
    private int sampleEventHandledCount;

    public void sampleEventHandled() {
        sampleEventHandledCount++;
    }

    public void anotherEventHandled() {
        anotherEventHandledCount++;
    }

    public void sagaCompleted() {
        sagaCompletedCount++;
    }

    public int getSampleEventHandledCount() {
        return sampleEventHandledCount;
    }

    public int getAnotherEventHandledCount() {
        return anotherEventHandledCount;
    }

    public int getSagaCompletedCount() {
        return sagaCompletedCount;
    }
}
