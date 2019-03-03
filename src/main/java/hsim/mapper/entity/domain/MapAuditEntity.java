package hsim.mapper.entity.domain;

import hsim.mapper.entity.annotation.IgnoreUpdateFromObj;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * The type Map audit entity.
 */
@MappedSuperclass
@Getter
public abstract class MapAuditEntity extends MapBaseEntity {

    @Column(nullable = false, updatable = false)
    @IgnoreUpdateFromObj
    private Date createdAt;

    @Column(nullable = false)
    @IgnoreUpdateFromObj
    private Date updatedAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = new Date();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = new Date();
    }
}
