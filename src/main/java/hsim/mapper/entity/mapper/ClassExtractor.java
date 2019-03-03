package hsim.mapper.entity.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type Class extractor.
 */
public class ClassExtractor {

    /**
     * Extract fields list.
     *
     * @param type the type
     * @return the list
     */
    public static List<Field> extractFields(Class type) {
        List<Field> fields = Arrays.stream(type.getDeclaredFields()).collect(Collectors.toList());
        if (!type.getSuperclass().equals(Object.class)) {
            fields.addAll(extractFields(type.getSuperclass()));
        }
        return fields;
    }

    /**
     * Extract methods list.
     *
     * @param type   the type
     * @param filter the filter
     * @return the list
     */
    public static List<Method> extractMethods(Class type, Function<Method, Boolean> filter) {
        List<Method> methods = new ArrayList<>();
        for (Method method : type.getDeclaredMethods()) {
            if (filter.apply(method)) {
                methods.add(method);
            }
        }
        if (!type.getSuperclass().equals(Object.class)) {
            methods.addAll(extractMethods(type.getSuperclass(), filter));
        }
        return methods;
    }
}
