package hsim.mapper.entity.type;

import hsim.mapper.entity.contants.Constants;
import lombok.NonNull;
import org.joda.time.DateTime;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

/**
 * The enum Type map.
 */
public enum TypeMap {

    /**
     * The Long.
     */
    Long_(new Class[]{long.class, Long.class}, new Class[]{int.class, Integer.class, Short.class, short.class}, v -> v, v -> Long.valueOf(toString(v))),
    /**
     * The Int.
     */
    Int_(new Class[]{int.class, Integer.class}, new Class[]{Short.class, short.class}, v -> v, v -> Integer.valueOf(toString(v))),
    /**
     * The Double.
     */
    Double_(new Class[]{double.class, Double.class}, new Class[]{Float.class, float.class, int.class, Integer.class, Short.class, short.class}, v -> v, v -> Double.valueOf(toString(v))),
    /**
     * The Float.
     */
    Float_(new Class[]{float.class, Float.class}, new Class[]{int.class, Integer.class, Short.class, short.class}, v -> v, v -> Float.valueOf(toString(v))),
    /**
     * The String.
     */
    String_(new Class[]{String.class}, new Class[]{}, v -> v, v -> toString(v)),

    /**
     * The Date.
     */
    Date_(new Class[]{Date.class}, new Class[]{Long.class, long.class}, v -> new Date(((Date) v).getTime()), v -> new Date((Long) v)),
    /**
     * The Date str.
     */
    DateStr_(new Class[]{Date.class}, new Class[]{String.class}, v -> new Date(((Date) v).getTime()), v -> new DateTime(v).toDate()),
    /**
     * The Date time.
     */
    DateTime_(new Class[]{DateTime.class}, new Class[]{Date.class}, v -> new DateTime(((Date) v).getTime()), v -> new DateTime(v)),
    /**
     * The Date date time.
     */
    DateDateTime_(new Class[]{Date.class}, new Class[]{DateTime.class}, v -> new Date(((Date) v).getTime()), v -> ((DateTime) v).toDate());

    private final Class[] to;
    private final Class[] from;
    private final Function<Object, Object> copy;
    private final Function<Object, Object> cast;

    TypeMap(Class[] to, Class[] from, Function<Object, Object> copy, Function<Object, Object> cast) {
        this.to = to;
        this.from = from;
        this.copy = copy;
        this.cast = cast;
    }

    /**
     * Cast t.
     *
     * @param <T>    the type parameter
     * @param obj    the obj
     * @param target the target
     * @return the t
     */
    public static <T> T cast(@NonNull Object obj, @NonNull Class<T> target) {
        if (target.isEnum()) {
            return enumCast(obj, target);
        }

        TypeMap caster = Arrays.stream(TypeMap.values()).filter(typeMap -> typeMap.isSupportType(target, obj.getClass())).findFirst().orElse(null);
        if (caster != null && obj.getClass().equals(target)) {
            return target.cast(caster.copy.apply(obj));
        } else if (caster != null && !obj.getClass().equals(target)) {
            return target.cast(caster.cast.apply(obj));
        } else if (obj.getClass().equals(target)) {
            return target.cast(obj);
        } else {
            return null;
        }

    }

    /**
     * Enum cast t.
     *
     * @param <T>    the type parameter
     * @param obj    the obj
     * @param target the target
     * @return the t
     */
    public static <T> T enumCast(@NonNull Object obj, @NonNull Class<T> target) {
        if (obj.getClass().isEnum() && obj.getClass().equals(target)) {
            return target.cast(obj);
        }

        try {
            Method valueOf = target.getMethod(Constants.ENUM_CAST_METHOD, String.class);
            return target.cast(valueOf.invoke(target, toString(obj)));
        } catch (Exception anyException) {
            return null;
        }
    }

    private static String toString(Object obj) {
        return obj + "";
    }

    private boolean isSupportType(Class to, Class from) {

        for (Class t : this.to) {
            if (t.equals(to)) {
                return t.equals(from) || isAllSupport() || Arrays.stream(this.from).anyMatch(support -> from.equals(support));
            }
        }
        return false;
    }

    private boolean isAllSupport() {
        return this.from.length < 1;
    }

}
