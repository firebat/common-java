package common.concurrent;

/**
 * 轻量可运行命名任务
 */
public abstract class NamedRunnable implements Runnable {

    private String name;

    public NamedRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        final Thread thread = Thread.currentThread();
        final String threadName = thread.getName();

        try {
            thread.setName(name);
            doRun();
        } finally {
            thread.setName(threadName);
        }
    }

    public abstract void doRun();

    public String getName() {
        return name;
    }
}
