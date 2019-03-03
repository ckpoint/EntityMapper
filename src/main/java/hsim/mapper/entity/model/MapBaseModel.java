package hsim.mapper.entity.model;


import lombok.Data;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public abstract class MapBaseModel extends MapModel {
    private Long id;
}


