package pl.com.bottega.ddd.domain;

import java.sql.Timestamp;

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

    /**
     * Two entities are equal if they are the same instance or both are the same
     * class and have equal id.
     * 
     * If they are the same class but both have null id they are not equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        BaseEntity entity = (BaseEntity) obj;
        if (entityId == null || entity.entityId == null) {
            return false;
        }
        return entityId.equals(entity.entityId);
    }

    @Override
    public int hashCode() {
        return entityId == null ? 0 : entityId.hashCode();
    }
}
