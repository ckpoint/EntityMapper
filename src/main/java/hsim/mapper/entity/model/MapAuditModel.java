package hsim.mapper.entity.model;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * The type Map audit model.
 */
@MappedSuperclass
@Data
public abstract class MapAuditModel extends MapBaseModel {
    private Date createdAt;
    private Date updatedAt;
}
