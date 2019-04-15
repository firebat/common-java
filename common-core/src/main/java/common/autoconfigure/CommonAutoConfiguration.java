package common.autoconfigure;

import common.management.ServerLifecycle;
import common.management.ServerManager;
import common.monitor.influx.InfluxDBLifecycle;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class CommonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CommonProperties commonProperties() {
        return new CommonProperties();
    }

    @Bean
    public SmartLifecycle serverLifecycle(CommonProperties commonProperties) {
        return new ServerLifecycle(ServerManager.get(), commonProperties);
    }

    @Bean
    @ConditionalOnClass(name = "org.influxdb.InfluxDB")
    public InfluxDBLifecycle influxDBLifecycle(CommonProperties commonProperties) {
        return new InfluxDBLifecycle(commonProperties.getMonitor().getInflux());
    }
}
