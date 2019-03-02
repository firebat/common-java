package common.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

public class Closer {

    private static final Logger logger = LoggerFactory.getLogger(Closer.class);

    public static void close(Closeable... cs) {
        if (cs == null)
            return;
        for (Closeable c : cs) {
            if (c != null) {
                try {
                    c.close();
                } catch (Throwable e) {
                    logger.warn("资源关闭时出错:" + c.getClass().getName(), e);
                }
            }
        }
    }

    public static void close(Iterable<Closeable> cs) {
        if (cs == null)
            return;
        for (Closeable c : cs) {
            if (c != null) {
                try {
                    c.close();
                } catch (Throwable e) {
                    logger.warn("资源关闭时出错:" + c.getClass().getName(), e);
                }
            }
        }
    }
}
