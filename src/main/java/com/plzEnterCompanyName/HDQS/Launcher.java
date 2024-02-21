package com.plzEnterCompanyName.HDQS;

import com.plzEnterCompanyName.HDQS.io.PATH;
import com.plzEnterCompanyName.HDQS.ref.ReadJar;
import com.plzEnterCompanyName.HDQS.text.LinePrinter;
import com.plzEnterCompanyName.HDQS.text.Windows10LinePrinter;
import com.plzEnterCompanyName.HDQS.util.args.Argument;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 游戏的启动类，管理启动流程，进行文件检查，没有GUI。
 * </p>
 * <p>
 * 为了提高程序的可拓展性，{@code Launcher}拥有独特的启动模式。具体流程如下：
 * <blockquote>
 * 
 * <pre>
 *     扫描bin\HDQS.jar，遍历其中的.class文件
 *     实现了RequireBoot的类被加载为类对象，放入REQUIRE_BOOT集合。
 *     实现了Argument的类被加载为类对象，放入OPTIONS集合。
 *     boot开始，遍历REQUIRE_BOOT中的元素，完成加载
 *     遍历OPTIONS中的元素，对可能的传入参数进行处理
 *     进入游戏或是进入Argument的实现类提供的入口
 *     ......
 *     运行cleanUp()
 *     退出游戏
 * </pre>
 * 
 * </blockquote>
 * </p>
 */
public class Launcher {

    private static final PrintStream DEFAULT_PRINT = System.out;
    private static final LinePrinter LINE_PRINTER = new Windows10LinePrinter(DEFAULT_PRINT);
    private static final List<Class<?>> REQUIRE_BOOT;
    private static final List<Class<?>> OPTIONS;
    static {
        REQUIRE_BOOT = new ArrayList<>();
        OPTIONS = new ArrayList<>();
        File HDQS_jar = PATH.getFile("bin\\HDQS.jar");
        LINE_PRINTER.printb(HDQS_jar);
        ReadJar reader = new ReadJar(HDQS_jar);
        List<Class<?>> classes = reader.getAllClass();
        for (Class<?> clazz : classes) {
            for (Class<?> interfaceClass : clazz.getInterfaces()) {
                if (RequireBoot.class.equals(interfaceClass)) {
                    REQUIRE_BOOT.add(clazz);
                } else if (Argument.class.equals(interfaceClass)) {
                    OPTIONS.add(clazz);
                }
            }
        }
    }

    /*
     * 当该值为null时，正常进入游戏。
     * 当该值不为null时，进入该值提供的入口，不进入游戏
     */
    private static Start launch = null;

    /**
     * 用于改变程序的运作模式。详情请见{@link Start}
     * 
     * @param argStart 提供入口的Start实例
     */
    public static void setLaunch(Start argStart) {
        if (launch != null) {
            throw new UnsupportedOperationException();
        } else {
            launch = argStart;
        }
    }

    /* No Launcher instance for you */
    private Launcher() {
    }

    public static void main(String[] args) {
        boot();
        processArgs(args);
        if (launch == null) {
            // 进入游戏
        } else {
            launch.main();
        }
        cleanUp();
    }

    /**
     * 启动，启动的流程以放在类注释中，不再赘述。
     */
    private static void boot() {
        long startTime = System.currentTimeMillis();
        // 通过class对象实例化并运行
        for (Class<?> clazz : REQUIRE_BOOT) {
            Object obj;
            try {
                // 获取Constructor，进而新建实例。
                Constructor<?> constructor = clazz.getConstructor();
                obj = constructor.newInstance();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException
                    | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            Objects.requireNonNull(obj);
            if (obj instanceof RequireBoot rb) {
                // 调用boot()方法，完成启动。
                // 有时，启动会消耗一点时间，这里可以展示进度。
                rb.boot(LINE_PRINTER);
            }
        }
        long cost = System.currentTimeMillis() - startTime;
        DEFAULT_PRINT.println();
        DEFAULT_PRINT.println("Boot ended. Cost " + cost + " ms");
    }

    /*
     * 处理可能的args..
     */
    private static void processArgs(String[] args) {
        if (args.length == 0) {
            return;
        }
        // 通过OPTIONS集合创建实例，放入Map集合中以便调用
        // Map<String, Argument> argsProcessors
        // Key代表遇到对应的arg，Value代表要进行的操作
        Map<String, Argument> argsProcessors = new HashMap<>();
        for (Class<?> clazz : OPTIONS) {
            Object obj;
            try {
                Constructor<?> constructor = clazz.getConstructor();
                obj = constructor.newInstance();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException
                    | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            Objects.requireNonNull(obj);
            if (obj instanceof Argument argProcessor) {
                argsProcessors.put(argProcessor.getParam(), argProcessor);
            }
        }
        // 把String[] args转换为便于程序读取与遍历的结构
        // Map<String, String[]> optionsMap
        // Key代表实际传入的arg，带有'-'开头，Value代表arg后不以'-'开头的arg
        Map<String, String[]> optionsMap = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String hToken = args[i];
            if (hToken.startsWith("-")) {
                List<String> options = new ArrayList<>();
                for (int j = i + 1; j < args.length; j++) {
                    if (args[j].startsWith("-")) {
                        break;
                    }
                    options.add(args[j]);
                }
                String[] optionsArray = new String[options.size()];
                for (int j = 0; j < options.size(); j++) {
                    optionsArray[j] = options.get(j);
                }
                optionsMap.put(hToken, optionsArray);
            }
        }
        // 运行，处理
        for (Map.Entry<String, String[]> option : optionsMap.entrySet()) {
            Argument argProcessor = argsProcessors.get(option.getKey());
            if (argProcessor != null) {
                Start processorStart = argProcessor.process(option.getValue());
                if (processorStart != null) {
                    setLaunch(processorStart);
                }
            }
            // ================================
            // Debug code, do not delete
            // ================================
            // String p = "";
            // p += "{";
            // p += option.getKey();
            // p += "=";
            // p += Arrays.toString(option.getValue());
            // p += "}";
            // System.out.println(p);
        }
    }

    private static void cleanUp() {

    }
}
