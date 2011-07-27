package pl.com.bottega.ddd.sagas;

import org.springframework.stereotype.Component;

@Component
public class SagaSpy {

    private boolean methodCalled;

    public void callMethod() {
        methodCalled = true;

    }

    public boolean methodHasBeenCalled() {
        return methodCalled;
    }

}
