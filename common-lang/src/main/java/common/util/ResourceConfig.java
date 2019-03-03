package common.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import common.io.Closer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 从资源中加载Properties文件
 */
public class ResourceConfig {

    private Properties properties = new Properties();

    public static ResourceConfig getOrNull(String name) {
        try {
            return new ResourceConfig(name);
        } catch (Exception e) {
            return null;
        }
    }

    public ResourceConfig(String name) {

        Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "配置文件名缺失");

        final InputStream is = streamOf(name);
        Preconditions.checkArgument(is != null, "配置文件不存在");

        try {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("无法加载" + name, e);
        } finally {
            Closer.close(is);
        }
    }

    public static InputStream streamOf(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    }

    public Properties getProperties() {
        return properties;
    }
}
