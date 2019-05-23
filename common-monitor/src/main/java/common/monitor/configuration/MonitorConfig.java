package common.monitor.configuration;

import common.monitor.influx.InfluxDBConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;


@ConfigurationProperties("common.monitor")
public class MonitorConfig {

    private String url = "/monitor";

    @NestedConfigurationProperty
    private InfluxDBConfig influx = new InfluxDBConfig();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InfluxDBConfig getInflux() {
        return influx;
    }
}
