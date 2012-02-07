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
package pl.com.bottega.ddd.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;


/**
 * @author Slawek
 * 
 */
@MappedSuperclass
public abstract class BaseAggregateRoot extends BaseEntity {

    /**
     * Sample of Domain Event usage<br>
     * Event Publisher is injected by Factory/Repo
     */
    @Transient
    protected DomainEventPublisher eventPublisher;

    /**
     * Sample technique of injecting Event Publisher into the Aggregate.<br>
     * <br>
     * Can be called only once by Factory/Repository<br>
     * Visible for package (Factory/Repository)
     */
    public void setEventPubslisher(DomainEventPublisher domainEventPubslisher) {
        if (this.eventPublisher != null)
            throw new IllegalStateException("Publisher is already set! Probably You have logical error in code");
        this.eventPublisher = domainEventPubslisher;
    }
}
