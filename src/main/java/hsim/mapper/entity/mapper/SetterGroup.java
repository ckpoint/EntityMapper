package hsim.mapper.entity.mapper;

import hsim.mapper.entity.contants.Constants;
import hsim.mapper.entity.exception.NotSupportedException;
import hsim.mapper.entity.type.TypeMap;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Setter group.
 */
public class SetterGroup {

    private final Map<String, ValueMapper> map = new HashMap<>();
    private final Object to;

    /**
     * Instantiates a new Setter group.
     *
     * @param object the object
     * @throws NotSupportedException the not supported exception
     */
    public SetterGroup(@NonNull Object object) throws NotSupportedException {
        this.to = object;
        this.extractMethods();
        this.extractFields();
    }

    /**
     * Sets .
     *
     * @param getterGroup the getter group
     */
    public void sets(GetterGroup getterGroup) {
        for (Map.Entry<String, ValueMapper> entry : this.map.entrySet()) {
            ValueMapper valueMapper = entry.getValue();
            if (valueMapper.isIgnore()) {
                continue;
            }
            String fieldName = entry.getKey();
            if (getterGroup.has(fieldName)) {
                this.set(fieldName, getterGroup.get(fieldName));
            }
        }
    }

    /**
     * Set.
     *
     * @param field the field
     * @param value the value
     */
    public void set(String field, Object value) {
        ValueMapper valueMapper = this.map.get(field);
        if (valueMapper == null) {
            return;
        }

        try {
            if (value == null) {
                valueMapper.set(this.to, null);
            } else {
                Object castValue = TypeMap.cast(value, valueMapper.getTypeClass());
                valueMapper.set(this.to, castValue != null ? castValue : value);
            }
        } catch (NotSupportedException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void extractFields() throws NotSupportedException {
        for (Field field : ClassExtractor.extractFields(this.to.getClass())) {
            FieldMapper fieldMapper = new FieldMapper(field);
            this.put(fieldMapper.getFieldName(), fieldMapper);
        }
    }

    private void extractMethods() throws NotSupportedException {
        for (Method method : ClassExtractor.extractMethods(this.to.getClass(), method -> method.getName().startsWith(Constants.SETTER_PREFIX))) {
            MethodMapper methodMapper = new MethodMapper(method);
            this.put(methodMapper.getFieldName(), methodMapper);
        }
    }

    private void put(String key, ValueMapper valueMapper) {
        if (this.map.get(key) != null) {
            return;
        }
        this.map.put(key, valueMapper);
    }
}
