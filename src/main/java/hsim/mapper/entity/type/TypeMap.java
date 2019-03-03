package hsim.mapper.entity.type;

import hsim.mapper.entity.contants.Constants;
import lombok.NonNull;
import org.joda.time.DateTime;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

public enum TypeMap {

    Long_(new Class[]{long.class, Long.class}, new Class[]{int.class, Integer.class, Short.class, short.class}, v -> v, v -> Long.valueOf(toString(v))),
    Int_(new Class[]{int.class, Integer.class}, new Class[]{int.class, Integer.class, Short.class, short.class}, v -> v, v -> Integer.valueOf(toString(v))),
    Double_(new Class[]{double.class, Double.class}, new Class[]{Float.class, float.class, int.class, Integer.class, Short.class, short.class}, v -> v, v -> Double.valueOf(toString(v))),
    Float_(new Class[]{float.class, Float.class}, new Class[]{int.class, Integer.class, Short.class, short.class}, v -> v, v -> Float.valueOf(toString(v))),
    String_(new Class[]{String.class}, new Class[]{}, v -> v, v -> toString(v)),

    Date_(new Class[]{Date.class}, new Class[]{Long.class, long.class}, v -> new Date(((Date) v).getTime()), v -> new Date((Long) v)),
    DateStr_(new Class[]{Date.class}, new Class[]{String.class}, v -> new Date(((Date) v).getTime()), v -> new DateTime(v).toDate()),
    DateTime_(new Class[]{DateTime.class}, new Class[]{Date.class}, v -> new DateTime(((Date) v).getTime()), v -> new DateTime(v)),
    DateDateTime_(new Class[]{Date.class}, new Class[]{DateTime.class}, v -> new Date(((Date) v).getTime()), v -> ((DateTime)v).toDate());

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


    private static String toString(Object obj) {
        return obj + "";
    }

}
