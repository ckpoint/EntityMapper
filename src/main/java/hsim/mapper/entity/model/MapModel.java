package hsim.mapper.entity.model;


import hsim.mapper.entity.exception.NotSupportedException;
import hsim.mapper.entity.mapper.GetterGroup;
import hsim.mapper.entity.mapper.SetterGroup;

import javax.persistence.MappedSuperclass;

/**
 * The type Map model.
 */
@MappedSuperclass
public abstract class MapModel {

    /**
     * Update from obj.
     *
     * @param obj the obj
     */
    public void updateFromObj(Object obj) {
        try {
            GetterGroup getterGroup = new GetterGroup(obj);
            SetterGroup setterGroup = new SetterGroup(this);
            setterGroup.sets(getterGroup);
        } catch (NotSupportedException e) {
            e.printStackTrace();
        }
    }
}


