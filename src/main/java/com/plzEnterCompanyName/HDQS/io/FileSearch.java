package com.plzEnterCompanyName.HDQS.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 从某一目录中查找特定的文件
 * 现有以下功能
 * <pre>
 *     - 按后缀查找
 * </pre>
 */
public class FileSearch {
    /* 不应被实例化 */
    private FileSearch() {}

    /**
     * 查找特定后缀名的文件
     * @param dir 需要查找的目录
     * @param suffix 需要查找的后缀
     * @return 所有满足条件的文件对象
     */
    public static List<File> suffix(File dir, String suffix) {
        List<File> returnVal = new ArrayList<>();
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                returnVal.addAll(suffix(file, suffix));
            } else if (file.getAbsolutePath().endsWith(suffix)) {
                returnVal.add(file);
            }
        }
        return returnVal;
    }
}
