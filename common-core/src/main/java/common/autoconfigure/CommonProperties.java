package common.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties("common")
public class CommonProperties {

    @NestedConfigurationProperty
    private MonitorConfig monitor = new MonitorConfig();

    @NestedConfigurationProperty
    private ServerConfig server = new ServerConfig();

    public MonitorConfig getMonitor() {
        return monitor;
    }

    public ServerConfig getServer() {
        return server;
    }
}
