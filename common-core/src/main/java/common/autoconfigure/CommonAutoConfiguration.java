package common.autoconfigure;

import common.management.ServerLifecycle;
import common.management.ServerManager;
import common.management.health.HealthCheckServlet;
import common.monitor.MonitorServlet;
import common.monitor.influx.InfluxDBLifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class CommonAutoConfiguration {

    private Logger log = LoggerFactory.getLogger(CommonAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public CommonProperties commonProperties() {
        return new CommonProperties();
    }

    @Bean
    public ServletRegistrationBean monitorServlet(CommonProperties commonProperties) {
        MonitorConfig config = commonProperties.getMonitor();
        log.info("monitor url={}", config.getUrl());
        return new ServletRegistrationBean<>(new MonitorServlet(), config.getUrl());
    }

    @Bean
    public ServletRegistrationBean healthServlet(CommonProperties commonProperties) {
        HealthCheckConfig config = commonProperties.getServer().getHealthCheck();
        log.info("healthCheck url={}", config.getUrl());
        return new ServletRegistrationBean<>(new HealthCheckServlet(), config.getUrl());
    }

    @Bean
    public ServerLifecycle serverLifecycle(CommonProperties commonProperties) {
        return new ServerLifecycle(ServerManager.get(), commonProperties);
    }

    @Bean
    @ConditionalOnClass(name = "org.influxdb.InfluxDB")
    public InfluxDBLifecycle influxDBLifecycle(CommonProperties commonProperties) {
        return new InfluxDBLifecycle(commonProperties.getMonitor().getInflux());
    }
}
