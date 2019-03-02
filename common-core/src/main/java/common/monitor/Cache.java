package common.monitor;

import java.util.Collections;
import java.util.Map;

public class Cache {

    private long timestamp = 0;
    private Map<String, Long> values = Collections.emptyMap();
    private Map<String, Timer> timers = Collections.emptyMap();

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Map<String, Long> getValues() {
        return values;
    }

    public void setValues(Map<String, Long> values) {
        this.values = values;
    }

    public Map<String, Timer> getTimers() {
        return timers;
    }

    public void setTimers(Map<String, Timer> timers) {
        this.timers = timers;
    }
}
