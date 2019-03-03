package hsim.mapper.entity.domain;


import hsim.mapper.entity.exception.NotSupportedException;
import hsim.mapper.entity.mapper.FieldUpdator;

import javax.persistence.MappedSuperclass;


/**
 * The type Map entity.
 */
@MappedSuperclass
public abstract class MapEntity {

    /**
     * Update from obj.
     *
     * @param obj the obj
     */
    public void updateFromObj(Object obj) {
        try {
            FieldUpdator fieldUpdator = new FieldUpdator(obj, this);
            fieldUpdator.update();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        }
    }
}


