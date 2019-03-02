package common.concurrent;

import java.util.concurrent.*;

/**
 * 共享线程池
 */
public class ManagedExecutors {

    private ManagedExecutors() {
    }

    private static final ThreadPoolExecutor executorService = (ThreadPoolExecutor) newCachedThreadPool("shared-pool");
    private static final ScheduledThreadPoolExecutor scheduledExecutorService = (ScheduledThreadPoolExecutor) newScheduledThreadPool(10, "shared-sched-pool");

    public static ExecutorService getExecutor() {
        return executorService;
    }

    public static ScheduledExecutorService getScheduledExecutor() {
        return scheduledExecutorService;
    }

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
    }

    public static ExecutorService newFixedThreadPool(int nThreads, String threadName) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(), new NamedThreadFactory(threadName));
    }

    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
    }

    public static ExecutorService newCachedThreadPool(String threadName) {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new NamedThreadFactory(threadName));
    }

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return new ScheduledThreadPoolExecutor(corePoolSize);
    }

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize, String threadName) {
        return new ScheduledThreadPoolExecutor(corePoolSize, new NamedThreadFactory(threadName));
    }
}
