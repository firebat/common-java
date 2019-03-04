package common.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class SleepyTask implements Runnable {

    private final AtomicBoolean ready = new AtomicBoolean();
    private final AtomicBoolean running = new AtomicBoolean();

    protected final Executor executor;

    public SleepyTask(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void run() {
        try {
            while (ready.compareAndSet(true, false)) {
                runTask();
            }
        } finally {
            running.set(false);
        }
    }

    public boolean wakeup() {
        ready.set(true);
        if (running.compareAndSet(false, true)) {
            executor.execute(this);
            return true;
        }
        return false;
    }

    protected abstract void runTask();
}
