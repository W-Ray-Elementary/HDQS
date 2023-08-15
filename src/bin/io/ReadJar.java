package bin.io;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 将jar包中的class以对象形式提取出来
 */
public class ReadJar {
    private final File target;
    private final List<Class<?>> allClass;

    public ReadJar(File target) {
        this.target = target;
        allClass = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        URL url1;
        try {
            url1 = target.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        JarFile jarFile = null;
        URLClassLoader jarClassLoader = null;
        try {
            jarClassLoader = new URLClassLoader(new URL[]{ url1 },
                    Thread.currentThread().getContextClassLoader());
            jarFile = new JarFile(target);
            Enumeration<JarEntry> entries = jarFile.entries();
            List<String> classNames = getClassNames(entries);
            assert classNames != null;
            for (String x : classNames)
                loadClass(x, jarClassLoader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (jarClassLoader != null) {
                try {
                    jarClassLoader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<String> getClassNames(Enumeration<JarEntry> entries) {
        List<String> classNames = new ArrayList<>();
        while (entries.hasMoreElements()) {
            JarEntry nextEle = entries.nextElement();
            String name = nextEle.getName();
            if (name.endsWith(".class")) {
                String replace = name.replace(".class", "").replace("/", ".");
                classNames.add(replace);
            }
        }
        return classNames;
    }

    private void loadClass(String x, ClassLoader classLoader) {
        try {
            allClass.add(classLoader.loadClass(x));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Class<?>> getAllClass() {
        return allClass;
    }
}
