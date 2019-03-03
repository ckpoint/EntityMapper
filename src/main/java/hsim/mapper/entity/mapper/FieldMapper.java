package hsim.mapper.entity.mapper;

import hsim.mapper.entity.annotation.IgnoreUpdateFromObj;
import lombok.Getter;
import lombok.NonNull;

import javax.transaction.NotSupportedException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

@Getter
public class FieldMapper extends ValueMapper {

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
        return this.getField().getAnnotation(IgnoreUpdateFromObj.class) != null;
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
