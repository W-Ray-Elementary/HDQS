package com.plzEnterCompanyName.HDQS.io;

import java.io.File;

/**
 * 功能十分简单的一个工具类。
 * 专用于解决IDE与cmd中相对路径不同的问题，
 * 领文件对象一定要准备好以下材料，按目录顺序依次叠放
 *  - 文件相对路径
 * 注意文件相对路径要有效，不然你自己惹出麻烦来本类概不负责。
 */
public class PATH {

    /**
     * 类加载时，此变量就被确定
     * <p>注意：{@code ABSOLUTE_ROOT_PATH}<i>的末尾自动带有\\，勿重复添加！</i>
     */
    public static final String ABSOLUTE_ROOT_PATH;

    static {
        File runInIDEA = new File("out\\production\\HDQS\\com.plzEnterCompanyName.HDQS.Launcher.class");
        File runInCmd = new File("com.plzEnterCompanyName.HDQS.Launcher.class");
        if (runInIDEA.exists()) {
            ABSOLUTE_ROOT_PATH = runInIDEA.getAbsolutePath().substring(0, runInIDEA.getAbsolutePath().lastIndexOf('\\'));
        } else if (runInCmd.exists()) {
            ABSOLUTE_ROOT_PATH = runInCmd.getAbsolutePath().substring(0, runInCmd.getAbsolutePath().lastIndexOf('\\'));
        } else {
            throw new Error("Unsupported platform! Please reinstall this program.");
        }
    }

    /**
     * 除特别说明外，项目中出现的需要io流的位置都应当采用此方法获取File文件。
     * 否则可能出现随运行环境变化而相对路径变化导致文件读写出现异常。
     * @param relativePath 游戏目录中的相对位置。
     * @return 该文件对象如果未创建，此方法不会帮你创建，因为它只负责<i>找清你的定位</i>
     *         <i>形成自己的体系</i>需要靠你自己。
     */
    public static File getFile(String relativePath) {
        return new File(ABSOLUTE_ROOT_PATH + '\\' + relativePath);
    }

    /* 不应有PATH对象被创建 */
    private PATH() {}
}
