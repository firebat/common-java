package common.monitor.influx;

import common.config.ProxyConfig;
import common.monitor.Cache;
import common.monitor.MonitorWriter;
import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.impl.InfluxDBImpl;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

public class InfluxDBWriter implements MonitorWriter {

    private final InfluxDBConfig config;
    private final InfluxDB client;

    public InfluxDBWriter(InfluxDBConfig config) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(config.getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(config.getWriteTimeout(), TimeUnit.SECONDS);

        ProxyConfig proxy = config.getProxy();
        if (proxy.getType() != Proxy.Type.DIRECT) {
            builder.proxy(new Proxy(proxy.getType(), new InetSocketAddress(proxy.getHost(), proxy.getPort())));
        }

        this.config = config;
        this.client = new InfluxDBImpl(config.getUrl(), config.getUsername(), config.getPassword(), builder);

        if (config.isAutoCreate()) {
            client.createDatabase(config.getDatabase());
        }
    }

    @Override
    public void write(Cache cache) {

        BatchPoints points = BatchPoints.database(config.getDatabase()).build();

        cache.getValues().forEach((name, value) -> {
            points.point(Point.measurement(name)
                    .time(cache.getTimestamp(), TimeUnit.MILLISECONDS)
                    .tag(config.getTags())
                    .addField("value", value)
                    .build());
        });

        cache.getTimers().forEach((name, timer) -> {

            final long count = timer.getCount();
            final long duration = timer.getDuration();

            points.point(Point.measurement(name)
                    .time(cache.getTimestamp(), TimeUnit.MILLISECONDS)
                    .tag(config.getTags())
                    .addField("duration", duration)
                    .addField("count", count)
                    .addField("avg", count == 0 ? 0 : duration / count)
                    .addField("max", timer.getMax())
                    .addField("min", timer.getMin())

                    .build());
        });

        client.write(points);
    }

    public InfluxDB getClient() {
        return client;
    }
}
