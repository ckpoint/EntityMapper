package hsim.mapper.entity.mapper;

import hsim.mapper.entity.exception.NotSupportedException;
import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * The type Value mapper.
 */
@Getter
public abstract class ValueMapper {

    private final Class parentClass;
    private final Class typeClass;
    private final Type type;
    private final String fieldName;
    private final Method method;
    private final Field field;

    /**
     * Instantiates a new Value mapper.
     *
     * @param method the method
     * @param field  the field
     * @throws NotSupportedException the not supported exception
     */
    public ValueMapper(Method method, Field field) throws NotSupportedException {
        this.method = method;
        this.field = field;
        this.parentClass = this.getParentClass();
        this.typeClass = this.getTypeClass();
        this.type = this.getType();
        this.fieldName = this.getFieldName();

        this.validation();
    }

    /**
     * Is ignore boolean.
     *
     * @return the boolean
     */
    public abstract boolean isIgnore();

    /**
     * Gets type.
     *
     * @return the type
     */
    protected abstract Type getType();

    /**
     * Gets type class.
     *
     * @return the type class
     */
    protected abstract Class getTypeClass();

    /**
     * Gets field name.
     *
     * @return the field name
     */
    protected abstract String getFieldName();

    /**
     * Gets parent class.
     *
     * @return the parent class
     */
    protected abstract Class getParentClass();

    /**
     * Validation.
     *
     * @throws NotSupportedException the not supported exception
     */
    protected abstract void validation() throws NotSupportedException;


    /**
     * Set.
     *
     * @param obj   the obj
     * @param value the value
     * @throws NotSupportedException     the not supported exception
     * @throws InvocationTargetException the invocation target exception
     * @throws IllegalAccessException    the illegal access exception
     */
    public abstract void set(Object obj, Object value) throws NotSupportedException, InvocationTargetException, IllegalAccessException;

    /**
     * Get object.
     *
     * @param obj the obj
     * @return the object
     * @throws NotSupportedException     the not supported exception
     * @throws InvocationTargetException the invocation target exception
     * @throws IllegalAccessException    the illegal access exception
     */
    public abstract Object get(Object obj) throws NotSupportedException, InvocationTargetException, IllegalAccessException;

    /**
     * Is method boolean.
     *
     * @return the boolean
     */
    public boolean isMethod() {
        return this.method != null;
    }

    /**
     * Is field boolean.
     *
     * @return the boolean
     */
    public boolean isField() {
        return this.field != null;
    }


}
