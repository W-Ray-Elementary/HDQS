package io;

import com.plzEnterCompanyName.HDQS.io.PATH;
import com.plzEnterCompanyName.HDQS.io.ReadJar;
import com.plzEnterCompanyName.HDQS.util.NewInstance;
import pkg.Temp;

import java.util.List;

public class TestReadJar {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_isJarWorking();
        TEST_ReadJar();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("com.plzEnterCompanyName.HDQS.io.TestReadJar 的测试成功完成于" + ' ' + cost + ' ' + "ms");
    }

    private static void TEST_isJarWorking() {
        Temp t = new Temp();
        if (!"77a7c91a".equals(t.toString()))
            throw new RuntimeException("testRes/io下的GetCode[77a7c91a].jar不工作");
    }

    private static void TEST_ReadJar() {
        ReadJar rj = new ReadJar(PATH.getFile("testRes\\io\\GetCode[77a7c91a].jar"));
        List<Class<?>> allClass = rj.getAllClass();
        Object t = NewInstance.get(allClass.get(0));
        if (!"77a7c91a".equals(t.toString()))
            throw new RuntimeException("com.plzEnterCompanyName.HDQS.io.ReadJar未能读取到类");

    }
}
