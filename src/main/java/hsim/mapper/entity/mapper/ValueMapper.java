package hsim.mapper.entity.mapper;

import lombok.Getter;

import javax.transaction.NotSupportedException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@Getter
public abstract class ValueMapper {

    private final Class parentClass;
    private final Class typeClass;
    private final Type type;
    private final String fieldName;
    private final Method method;
    private final Field field;

    public ValueMapper(Method method, Field field) throws NotSupportedException {
        this.method = method;
        this.field = field;
        this.parentClass = this.getParentClass();
        this.typeClass = this.getTypeClass();
        this.type = this.getType();
        this.fieldName = this.getFieldName();

        this.validation();
    }

    public abstract boolean isIgnore();

    protected abstract Type getType();

    protected abstract Class getTypeClass();

    protected abstract String getFieldName();

    protected abstract Class getParentClass();

    protected abstract void validation() throws NotSupportedException;


    public abstract void set(Object obj, Object value) throws NotSupportedException, InvocationTargetException, IllegalAccessException;

    public abstract Object get(Object obj) throws NotSupportedException, InvocationTargetException, IllegalAccessException;

    public boolean isMethod() {
        return this.method != null;
    }

    public boolean isField() {
        return this.field != null;
    }


}
