package hsim.mapper.entity.mapper;

import hsim.mapper.entity.annotation.IgnoreUpdateFromObj;
import hsim.mapper.entity.exception.NotSupportedException;
import lombok.Getter;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

/**
 * The type Field mapper.
 */
@Getter
public class FieldMapper extends ValueMapper {

    /**
     * Instantiates a new Field mapper.
     *
     * @param field the field
     * @throws NotSupportedException the not supported exception
     */
    public FieldMapper(@NonNull Field field) throws NotSupportedException {
        super(null, field);
    }


    @Override
    public void set(Object obj, Object value) throws IllegalAccessException {
        this.getField().setAccessible(true);
        this.getField().set(obj, value);
    }

    @Override
    public Object get(Object obj) throws IllegalAccessException {
        this.getField().setAccessible(true);
        return this.getField().get(obj);
    }

    @Override
    public boolean isIgnore() {
        if (Modifier.isStatic(this.getField().getModifiers())) {
            return true;
        } else if (this.getField().getAnnotation(IgnoreUpdateFromObj.class) != null) {
            return true;
        }
        return false;
    }

    @Override
    protected Type getType() {
        return this.getField().getGenericType();
    }

    @Override
    protected Class getTypeClass() {
        return this.getField().getType();
    }

    @Override
    protected String getFieldName() {
        return this.getField().getName();
    }

    @Override
    protected Class getParentClass() {
        return this.getField().getDeclaringClass();
    }

    @Override
    protected void validation() {
    }
}
