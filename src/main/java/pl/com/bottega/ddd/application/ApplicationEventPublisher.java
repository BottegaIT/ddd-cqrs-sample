package pl.com.bottega.ddd.application;

import java.io.Serializable;

public interface ApplicationEventPublisher {

    void publish(Serializable applicationEvent);
}
