/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.com.bottega.ddd.sagas;

import javax.inject.Inject;

@Saga
public class SimpleSaga extends SagaInstance<SimpleSagaData> {

    private final SagaSpy spy;

    @Inject
    public SimpleSaga(SagaSpy spy) {
        this.spy = spy;
    }

    @SagaAction
    public void onSampleDomainEvent(SampleDomainEvent event) {
        data.setAggregateId(event.getAggregateId());
        spy.sampleEventHandled();
        completeIfPossible();
    }

    @SagaAction
    public void onAnotherDomainEvent(AnotherDomainEvent event) {
        data.setData(event.getData());
        spy.anotherEventHandled();
        completeIfPossible();
    }

    private void completeIfPossible() {
        if (data.getAggregateId() != null && data.getData() != null) {
            spy.sagaCompleted();
            markAsCompleted();
        }
    }
}
