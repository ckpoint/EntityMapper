package hsim.mapper.entity.mapper;

import hsim.mapper.entity.annotation.IgnoreUpdateFromObj;
import hsim.mapper.entity.contants.Constants;
import hsim.mapper.entity.exception.NotSupportedException;
import hsim.mapper.entity.type.TypeMap;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * The type Method mapper.
 */
@Getter
public class MethodMapper extends ValueMapper {

    private final static List<Class> notSupportReturnTypes = Arrays.asList(Void.class, void.class);

    /**
     * Instantiates a new Method mapper.
     *
     * @param method the method
     * @throws NotSupportedException the not supported exception
     */
    public MethodMapper(Method method) throws NotSupportedException {
        super(method, null);
    }

    @Override
    public void set(Object obj, Object value) throws NotSupportedException, InvocationTargetException, IllegalAccessException {
        if (!isSetter()) {
            throw new NotSupportedException(this.getMethod().getName() + " is not setter");
        }
        Object castValue = TypeMap.cast(value, this.getTypeClass());
        this.getMethod().invoke(obj, castValue);
    }

    @Override
    public Object get(Object obj) throws NotSupportedException, InvocationTargetException, IllegalAccessException {
        if (!(isGetter())) {
            throw new NotSupportedException(this.getMethod().getName() + " is not getter");
        }
        return this.getMethod().invoke(obj);
    }

    /**
     * Is setter boolean.
     *
     * @return the boolean
     */
    public boolean isSetter() {
        return this.getMethod().getName().startsWith(Constants.SETTER_PREFIX);
    }

    /**
     * Is getter boolean.
     *
     * @return the boolean
     */
    public boolean isGetter() {
        return this.getMethod().getName().startsWith(Constants.GETTER_PREFIX);
    }


    @Override
    public boolean isIgnore() {
        return this.getMethod().getAnnotation(IgnoreUpdateFromObj.class) != null;
    }

    @Override
    protected Type getType() {
        return this.isSetter() ? this.getMethod().getGenericParameterTypes()[0] : this.getMethod().getGenericReturnType();
    }

    @Override
    protected Class getTypeClass() {
        return this.isSetter() ? this.getMethod().getParameterTypes()[0] : this.getMethod().getReturnType();
    }

    @Override
    protected String getFieldName() {
        String removePrefix = this.isSetter() ? this.getMethod().getName().replaceFirst(Constants.SETTER_PREFIX, "")
                : this.getMethod().getName().replaceFirst(Constants.GETTER_PREFIX, "");
        return removePrefix.substring(0, 1).toLowerCase() + removePrefix.substring(1);
    }

    @Override
    protected Class getParentClass() {
        return this.getMethod().getDeclaringClass();
    }


    @Override
    protected void validation() throws NotSupportedException {
        this.commonValidation();

        if (isSetter()) {
            this.setterValidation();
        }

        if (isGetter()) {
            this.getterValidation();
        }
    }

    private void commonValidation() throws NotSupportedException {
        if (!isGetter() && !isSetter()) {
            throw new NotSupportedException(this.getMethod().getName() + " is not seeter or getter");
        }
    }

    private void setterValidation() throws NotSupportedException {
        if (this.getMethod().getGenericParameterTypes().length != 1) {
            throw new NotSupportedException(this.getMethod().getName() + " is invalid setter");
        }
    }

    private void getterValidation() throws NotSupportedException {
        if (notSupportReturnTypes.contains(this.getMethod().getReturnType())) {
            throw new NotSupportedException(this.getMethod().getName() + " not supported return type : " + this.getMethod().getReturnType().getName());
        }
    }


}
