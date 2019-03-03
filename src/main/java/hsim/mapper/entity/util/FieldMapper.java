package hsim.mapper.entity.util;

import hsim.mapper.entity.contants.Constants;
import hsim.mapper.entity.type.TypeMap;
import lombok.Getter;

import javax.transaction.NotSupportedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

@Getter
public class FieldMapper extends ValueMapper {

    private final static List<Class> notSupportReturnTypes = Arrays.asList(Void.class, void.class);

    public FieldMapper(Method method) throws NotSupportedException {
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
    public Object get(Object obj, Object value) throws NotSupportedException, InvocationTargetException, IllegalAccessException {
        if (!(isGetter())) {
            throw new NotSupportedException(this.getMethod().getName() + " is not getter");
        }
        return this.getMethod().invoke(obj);
    }

    public boolean isSetter() {
        return this.getMethod().getName().startsWith(Constants.SETTER_PREFIX);
    }

    public boolean isGetter() {
        return this.getMethod().getName().startsWith(Constants.GETTER_PREFIX);
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
