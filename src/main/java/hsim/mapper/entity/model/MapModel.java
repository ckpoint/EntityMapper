package hsim.mapper.entity.model;


import hsim.mapper.entity.mapper.GetterGroup;
import hsim.mapper.entity.mapper.SetterGroup;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.MappedSuperclass;
import javax.transaction.NotSupportedException;

@MappedSuperclass
@Slf4j
public abstract class MapModel {

    public void updateFromObj(Object obj) {
        try {
            GetterGroup getterGroup = new GetterGroup(obj);
            SetterGroup setterGroup = new SetterGroup(this);
            setterGroup.sets(getterGroup);
        } catch (NotSupportedException e) {
            log.info(e.getMessage());
        }
    }
}


