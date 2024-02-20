package com.plzEnterCompanyName.HDQS.io;

import com.plzEnterCompanyName.HDQS.SETTINGS;
import com.google.UnicodeReader.java.io.base.UnicodeReader;

import java.io.*;
import java.util.Objects;

/**
 * 用于内存与磁盘间String的交流，
 * @version 0.1a
 */
public class FileAndString {

    /**
     * <p>
     * 读取时请带上你的文件对象，没有的话去{@link com.plzEnterCompanyName.HDQS.io.PATH}领。要什么手续自己去那里问
     * 由于手续不全、文件有误导致的异常本类概不负责
     * </P>
     * <p>
     * 如果因为<i>磁盘</i>的失误导致异常，责任归<i>磁盘</i>所有！
     * </p>
     * @param file 建议从{@link com.plzEnterCompanyName.HDQS.io.PATH}获取
     * @return 在义务范围内为你提供的内容
     */
    public static String read(File file) {
        BufferedReader br = null;
        try {
            StringBuilder sb = new StringBuilder();
            br = new BufferedReader(new UnicodeReader(new FileInputStream(file), SETTINGS.ENCODE));
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s);
                sb.append('\n');
            }
            return Objects.toString(sb);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * <P>
     * 写入时请带上你的文件对象和写入内容
     * 注意，本类只负责写入，对文件内容没有知情权，不对文件内容负责！
     * </p>
     * <p>
     * 如果因为<i>磁盘</i>的失误导致异常，责任归<i>磁盘</i>所有！
     * </p>
     * @param file 建议从{@link com.plzEnterCompanyName.HDQS.io.PATH}获取
     * @param str 会写到文件中的内容
     */
    public static void write(File file, String str) {
        write(file, str, true);
    }

    /**
     * 防止有时候脑抽写反了形参
     */
    public static void write(String str, File file) {
        write(file, str, true);
    }

    /**
     * <P>
     * 写入时请带上你的文件对象和写入内容
     * 注意，本类只负责写入，对文件内容没有知情权，不对文件内容负责！
     * </p>
     * <p>
     * 如果因为<i>磁盘</i>的失误导致异常，责任归<i>磁盘</i>所有！
     * </p>
     * @param file 建议从{@link com.plzEnterCompanyName.HDQS.io.PATH}获取
     * @param str 会写到文件中的内容
     */
    public static void write(File file, String str, boolean autoCreateFile) {
        if (!file.getParentFile().exists() && autoCreateFile) {
            if (!file.getParentFile().mkdirs())
                throw new RuntimeException("Unexpected exception, please try to solve file writing problem.");
        }
        if (!file.exists() && autoCreateFile) {
            try {
                if (!file.createNewFile())
                    throw new RuntimeException("Unexpected exception, please try to solve file writing problem.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(new FileOutputStream(file), SETTINGS.ENCODE);
            osw.write(str);
            osw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
