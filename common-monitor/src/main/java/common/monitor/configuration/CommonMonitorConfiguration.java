package common.monitor.configuration;

import common.monitor.influx.InfluxDBLifecycle;
import common.monitor.servlet.MonitorServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class CommonMonitorConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MonitorConfig monitorConfig() {
        return new MonitorConfig();
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
