package hsim.mapper.entity.mapper;

import hsim.mapper.entity.exception.NotSupportedException;


/**
 * The type Field updator.
 */
public class FieldUpdator {

    private final GetterGroup getterGroup;
    private final SetterGroup setterGroup;

    /**
     * Instantiates a new Field updator.
     *
     * @param from the from
     * @param to   the to
     * @throws NotSupportedException the not supported exception
     */
    public FieldUpdator(Object from, Object to) throws NotSupportedException {
        this.getterGroup = new GetterGroup(from);
        this.setterGroup = new SetterGroup(to);
    }

    /**
     * Update.
     */
    public void update() {
        this.setterGroup.sets(this.getterGroup);
    }
}
