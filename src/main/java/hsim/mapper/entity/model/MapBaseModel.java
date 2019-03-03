package hsim.mapper.entity.model;


import lombok.Data;

import javax.persistence.MappedSuperclass;

/**
 * The type Map base model.
 */
@MappedSuperclass
@Data
public abstract class MapBaseModel extends MapModel {
    private Long id;
}


