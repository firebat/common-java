package common.monitor;

public class Timer {

    long count;
    long duration;

    synchronized Timer add(long count, long time) {
        this.count += count;
        this.duration += time;
        return this;
    }

    synchronized Timer set(long count, long time) {
        this.count = count;
        this.duration = time;
        return this;
    }

    synchronized Timer dumpAndClear() {
        Timer dump = new Timer().set(count, duration);
        this.count = 0;
        this.duration = 0;

        return dump;
    }

    public long getCount() {
        return count;
    }

    public long getDuration() {
        return duration;
    }
}
