package common.monitor.influx;

import com.google.common.base.Strings;
import common.monitor.Monitor;
import common.monitor.MonitorWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;

import java.util.Iterator;

public class InfluxDBLifecycle implements SmartLifecycle {

    private final Logger log = LoggerFactory.getLogger(InfluxDBLifecycle.class);

    private boolean running = false;
    private final InfluxDBWriter writer;

    public InfluxDBLifecycle(InfluxDBConfig config) {

        String url = config.getUrl();
        String database = config.getDatabase();

        if (Strings.isNullOrEmpty(url) || Strings.isNullOrEmpty(database)) {
            writer = null;
        } else {
            log.info("InfluxDB url={}, database={}, tags={}", url, database, config.getTags());
            writer = new InfluxDBWriter(config);
        }
    }

    @Override
    public void start() {
        running = true;

        if (writer != null) {
            log.info("InfluxDB start, version={}", writer.getClient().ping().getVersion());
            Monitor.getWriters().add(writer);
        }
    }

    @Override
    public void stop() {
        running = false;

        if (writer != null) {
            writer.getClient().close();
        }

        Iterator<MonitorWriter> i = Monitor.getWriters().iterator();
        while (i.hasNext()) {
            if (i.next() == writer) {
                i.remove();
                break;
            }
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
