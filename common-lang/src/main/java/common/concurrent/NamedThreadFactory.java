package common.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    private final AtomicInteger threadCount = new AtomicInteger(1);
    private final String prefix;
    private final boolean daemon;
    private final ThreadGroup group;

    public NamedThreadFactory(String prefix) {
        this(prefix, true);
    }

    public NamedThreadFactory(String prefix, boolean daemon) {
        this.prefix = prefix + "-thread-";
        this.daemon = daemon;
        SecurityManager sm = System.getSecurityManager();
        this.group = (sm == null) ? Thread.currentThread().getThreadGroup() : sm.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = prefix + threadCount.getAndIncrement();
        Thread thread = new Thread(group, r, name, 0);
        thread.setDaemon(daemon);
        return thread;
    }
}
