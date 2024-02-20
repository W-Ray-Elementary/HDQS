package com.plzEnterCompanyName.HDQS.ref;

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

    /**
     * jar包的文件，应保证不是目录，并且可读，本类会从此文件读取jar包中的class文件，
     * 并转换为对象。
     */
    private final File target;

    /**
     * 读取到jar包中的所有class文件创造的对象
     */
    private final List<Class<?>> allClass;

    /**
     * 构造一个ReadJar对象，并在此方法内完成读取class文件并实例化的任务
     * @param target 在此处的jar包开始搜索
     */
    public ReadJar(File target) {
        this.target = target;
        allClass = new ArrayList<>();
        initialize();
    }

    /*
    * 读取class文件并实例化
    * */
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

    /*
    * 把JarFile所有的JarEntry遍历，是class文件的就返回类名
    * */
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

    /*
    * 把单个class文件转为对应的class实例，需要ClassLoader
    * */
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
