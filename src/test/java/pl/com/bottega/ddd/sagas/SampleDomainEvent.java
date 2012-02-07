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

import pl.com.bottega.ddd.domain.DomainEvent;

public class SampleDomainEvent implements DomainEvent {

    private final Long aggregateId;

    public SampleDomainEvent(Long aggregateId) {
        this.aggregateId = aggregateId;
    }

    public Long getAggregateId() {
        return aggregateId;
    }
}
