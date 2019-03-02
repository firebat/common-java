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

    public void setMonitor(MonitorConfig monitor) {
        this.monitor = monitor;
    }

    public ServerConfig getServer() {
        return server;
    }

    public void setServer(ServerConfig server) {
        this.server = server;
    }
}
