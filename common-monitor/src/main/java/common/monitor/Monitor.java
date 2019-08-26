package common.monitor;

import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Monitor {

    private static ConcurrentHashMap<String, AtomicLong> values = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Timer> timers = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Timer> jvm = new ConcurrentHashMap<>();

    private static Cache cache = new Cache();
    private static List<MonitorWriter> writers = new ArrayList<>();

    private static java.util.Timer timer = new java.util.Timer("Monitor", true);

    static {
        timer.schedule(new TimerTask() {
            private long lastUpdate = 0;

            @Override
            public void run() {

                long current = System.currentTimeMillis();
                if (current - lastUpdate < 50000L) {
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(current);
                if (calendar.get(Calendar.SECOND) > 10) {
                    return;
                }

                // 每分钟前10秒内 执行一次切换
                lastUpdate = current;

                final Map<String, Long> cachedValues = new LinkedHashMap<>();
                final Map<String, Timer> cachedTimers = new LinkedHashMap<>();

                // JVM
                cachedValues.put("JVM_Thread_Count", (long) ManagementFactory.getThreadMXBean().getThreadCount());
                ManagementFactory.getGarbageCollectorMXBeans().stream().forEach(bean -> {
                    String name = "JVM_" + bean.getName();
                    long count = bean.getCollectionCount();
                    long time = bean.getCollectionTime();

                    Timer last = getOrCreateTimer(name, jvm);
                    cachedTimers.put(name, new Timer().set(count - last.count, time - last.duration));
                    last.set(count, time);
                });

                // Timers
                timers.forEach((name, item) -> cachedTimers.put(name, item.dumpAndClear()));
                values.forEach((name, item) -> cachedValues.put(name, item.getAndSet(0)));

                Cache cache = new Cache();
                cache.setTimestamp(lastUpdate);
                cache.setTimers(Collections.unmodifiableMap(cachedTimers));
                cache.setValues(Collections.unmodifiableMap(cachedValues));

                Monitor.cache = cache;

                for (MonitorWriter writer : writers) {
                    try {
                        writer.write(cache);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 2000);
    }

    public static Cache getCache() {
        return cache;
    }

    public static void setValue(String name, long value) {
        getOrCreateValue(name, values).set(value);
    }

    public static void recordValue(String name, long value) {
        getOrCreateValue(name, values).addAndGet(value);
    }

    public static void recordTime(String name, long time) {
        getOrCreateTimer(name, timers).add(1, time);
    }

    private static Timer getOrCreateTimer(String name, ConcurrentHashMap<String, Timer> timers) {
        Timer timer = timers.get(name);
        if (timer != null) {
            return timer;
        }

        Timer newTimer = new Timer();
        Timer stored = timers.putIfAbsent(name, newTimer);
        return stored != null ? stored : newTimer;
    }

    private static AtomicLong getOrCreateValue(String name, ConcurrentHashMap<String, AtomicLong> map) {
        AtomicLong counter = map.get(name);
        if (counter != null) {
            return counter;
        }

        AtomicLong newCounter = new AtomicLong();
        AtomicLong stored = map.putIfAbsent(name, newCounter);
        return stored != null ? stored : newCounter;
    }

    public static List<MonitorWriter> getWriters() {
        return writers;
    }
}
