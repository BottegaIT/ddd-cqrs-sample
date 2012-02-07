package pl.com.bottega.ddd.sagas;

import javax.inject.Singleton;

@Singleton
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
    
    public void reset()
    {
    	sagaCompletedCount = 0;
    	anotherEventHandledCount = 0;
    	sampleEventHandledCount = 0;
    }
}
