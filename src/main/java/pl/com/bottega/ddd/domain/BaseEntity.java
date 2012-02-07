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

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * 
 * @author Slawek
 * 
 */
@MappedSuperclass
public abstract class BaseEntity {

    // ALWAYS ADD NEW STATUS AT THE END - because the entityStatus field is
    // annotated as ordinal in sake of performance
    public static enum EntityStatus {
        ACTIVE, ARCHIVE
    }

    //entityId because ID can mean something (some domain concept) in some Bounded Context
    @Id
    @GeneratedValue
    private Long entityId;

    @SuppressWarnings("unused")
	@Version
    private Long version;

    @Enumerated(EnumType.ORDINAL)
    private EntityStatus entityStatus = EntityStatus.ACTIVE;

    public void markAsRemoved() {
        entityStatus = EntityStatus.ARCHIVE;
    }

    public Long getEntityId() {
        return entityId;
    }

    public EntityStatus getEntityStatus() {
        return entityStatus;
    }
}
