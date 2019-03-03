package hsim.mapper.entity.mapper;

import hsim.mapper.entity.contants.Constants;
import hsim.mapper.entity.exception.NotSupportedException;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Getter group.
 */
public class GetterGroup {

    private final Map<String, ValueMapper> map = new HashMap<>();
    private final Object from;

    /**
     * Instantiates a new Getter group.
     *
     * @param object the object
     * @throws NotSupportedException the not supported exception
     */
    public GetterGroup(@NonNull Object object) throws NotSupportedException {
        this.from = object;
        this.extractMethods();
        this.extractFields();
    }

    /**
     * Has boolean.
     *
     * @param field the field
     * @return the boolean
     */
    public boolean has(String field) {
        return this.map.get(field) != null;
    }

    /**
     * Get object.
     *
     * @param field the field
     * @return the object
     */
    public Object get(String field) {
        ValueMapper valueMapper = this.map.get(field);
        if (valueMapper == null) {
            return null;
        }

        try {
            return valueMapper.get(this.from);
        } catch (NotSupportedException | InvocationTargetException | IllegalAccessException e) {
            return null;
        }
    }

    private void extractFields() throws NotSupportedException {
        for (Field field : ClassExtractor.extractFields(this.from.getClass())) {
            FieldMapper fieldMapper = new FieldMapper(field);
            this.put(fieldMapper.getFieldName(), fieldMapper);
        }
    }

    private void extractMethods() throws NotSupportedException {
        for (Method method : ClassExtractor.extractMethods(this.from.getClass(), method -> method.getName().startsWith(Constants.GETTER_PREFIX))) {
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
