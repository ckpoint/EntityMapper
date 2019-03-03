package hsim.mapper.entity.mapper;

import javax.transaction.NotSupportedException;

public class FieldUpdator {

    private final GetterGroup getterGroup;
    private final SetterGroup setterGroup;

    public FieldUpdator(Object from, Object to) throws NotSupportedException {
        this.getterGroup = new GetterGroup(from);
        this.setterGroup = new SetterGroup(to);
    }

    public void update() {
        this.setterGroup.sets(this.getterGroup);
    }
}
