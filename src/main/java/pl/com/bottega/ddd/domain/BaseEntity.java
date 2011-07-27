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

    // ALWAYS ADD NEW STATUS AT THE END - because status field is annotated ad
    // ordinal in sake of performance
    public static enum EntityStatus {
        ACTIVE, ARCHIVE
    }

    @Id
    @GeneratedValue
    private Long id;

    @Version
    @SuppressWarnings("unused")
    private Timestamp lastModificationTime;

    @Enumerated(EnumType.ORDINAL)
    private EntityStatus entityStatus = EntityStatus.ACTIVE;

    public void markAsRemoved() {
        entityStatus = EntityStatus.ARCHIVE;
    }

    public Long getId() {
        return id;
    }

    public EntityStatus getEntityStatus() {
        return entityStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        // FIXME check this!
        if (!getClass().equals(obj.getClass())) {
            return false;
        }

        BaseEntity entity = (BaseEntity) obj;

        return id.equals(entity.id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
