package common.monitor.influx;

import com.google.common.collect.Maps;
import common.config.ClientConfig;
import common.config.ProxyConfig;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

public class InfluxDBConfig extends ClientConfig {

    @NestedConfigurationProperty
    private ProxyConfig proxy = new ProxyConfig();

    @NestedConfigurationProperty
    private Map<String, String> tags = Maps.newHashMap();

    private String url;
    private String username;
    private String password;
    private String database;
    private boolean autoCreate = false;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public boolean isAutoCreate() {
        return autoCreate;
    }

    public void setAutoCreate(boolean autoCreate) {
        this.autoCreate = autoCreate;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public ProxyConfig getProxy() {
        return proxy;
    }
}
