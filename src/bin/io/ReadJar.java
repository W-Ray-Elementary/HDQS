package bin.io;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * 将jar包中的class以对象形式提取出来
 */
public class ReadJar {
    private final File target;
    private List<Class<?>> allClazz;

    public ReadJar(File target) {
        this.target = target;
        initialize();
    }

    private void initialize() {
        URL url1 = null;
        try {
            url1 = target.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
}
