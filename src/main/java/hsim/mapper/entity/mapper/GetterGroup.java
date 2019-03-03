package hsim.mapper.entity.mapper;

import hsim.mapper.entity.contants.Constants;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.NotSupportedException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GetterGroup {

    private final Map<String, ValueMapper> map = new HashMap<>();
    private final Object from;

    public GetterGroup(@NonNull Object object) throws NotSupportedException {
        this.from = object;
        this.extractMethods();
        this.extractFields();
    }

    public boolean has(String field) {
        return this.map.get(field) != null;
    }

    public Object get(String field) {
        ValueMapper valueMapper = this.map.get(field);
        if (valueMapper == null) {
            return null;
        }

        try {
            return valueMapper.get(this.from);
        } catch (NotSupportedException | InvocationTargetException | IllegalAccessException e) {
            log.error(e.getMessage());
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
