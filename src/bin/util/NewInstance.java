package bin.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class NewInstance {
    private NewInstance() {}

    public static Object get(Class<?> clazz) {
        Object obj;
        try {
            Constructor<?> constructor = clazz.getConstructor();
            obj = constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        Objects.requireNonNull(obj);
        return obj;
    }
}
