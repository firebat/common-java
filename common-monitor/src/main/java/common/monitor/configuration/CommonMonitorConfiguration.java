package common.monitor.configuration;

import common.monitor.influx.InfluxDBLifecycle;
import common.monitor.servlet.MonitorServlet;
import common.net.LocalHost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableConfigurationProperties
public class CommonMonitorConfiguration {

    @Value("${server.address:localhost}")
    private String address;

    @Value("${server.port:8080}")
    private int port;

    @Bean
    @ConditionalOnMissingBean
    public MonitorConfig monitorConfig() {
        String host = "localhost".equals(address) ? LocalHost.getLocalHost() : address;
        MonitorConfig config = new MonitorConfig();
        Map tags = config.getInflux().getTags();
        tags.put("endpoint", host + "_" + port);
        return config;
    }

    @Bean
    public MonitorAspect monitorAspect() {
        return new MonitorAspect();
    }

    @Bean
    @ConditionalOnClass(name = "org.influxdb.InfluxDB")
    public InfluxDBLifecycle influxDBLifecycle(MonitorConfig monitorConfig) {
        return new InfluxDBLifecycle(monitorConfig.getInflux());
    }

    @Bean
    @ConditionalOnClass(name = "javax.servlet.http.HttpServlet")
    public ServletRegistrationBean monitorServlet(MonitorConfig monitorConfig) {
        return new ServletRegistrationBean<>(new MonitorServlet(), monitorConfig.getUrl());
    }
}
