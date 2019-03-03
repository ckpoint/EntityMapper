package hsim.mapper.entity.domain;


import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public abstract class MapEntity<T> {

    @Id
    @GeneratedValue
    private Long id;
}


