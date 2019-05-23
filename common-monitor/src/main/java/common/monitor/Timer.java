package common.monitor;

public class Timer {

    long count = 0;
    long duration = 0;
    long max = 0;
    long min = 0;

    synchronized Timer add(long count, long time) {
        this.count += count;
        this.duration += time;

        if (time > this.max) this.max = time;
        if (time < this.min || this.min == 0) this.min = time;
        return this;
    }

    synchronized Timer set(long count, long time) {
        this.count = count;
        this.duration = time;

        this.max = time;
        this.min = time;
        return this;
    }

    synchronized Timer dumpAndClear() {
        Timer dump = new Timer().set(count, duration);
        this.count = 0;
        this.duration = 0;
        this.max = 0;
        this.min = 0;

        return dump;
    }

    public long getCount() {
        return count;
    }

    public long getDuration() {
        return duration;
    }

    public long getMax() {
        return max;
    }

    public long getMin() {
        return min;
    }
}
