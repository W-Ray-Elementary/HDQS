package bin.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * 以class对象，创Object示例
 * （不好意思高考作文写多了）
 */
public class NewInstance {
    /* No NewInstance instance for you */
    private NewInstance() {}

    /**
     * 相当于{@code new}，但是是个方法
     * @param clazz 传入的的class对象必须能够实例化
     * @return 创建的对象会以Object的形式返回
     */
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
