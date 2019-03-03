package hsim.mapper.entity.domain;


import hsim.mapper.entity.annotation.IgnoreUpdateFromObj;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * The type Map base entity.
 */
@MappedSuperclass
@Getter
public abstract class MapBaseEntity extends MapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @IgnoreUpdateFromObj
    private Long id;
}


