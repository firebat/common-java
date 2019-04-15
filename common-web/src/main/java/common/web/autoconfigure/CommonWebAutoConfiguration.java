package common.web.autoconfigure;

import common.autoconfigure.CommonAutoConfiguration;
import common.autoconfigure.CommonProperties;
import common.autoconfigure.HealthCheckConfig;
import common.autoconfigure.MonitorConfig;
import common.web.servlet.HealthCheckServlet;
import common.web.servlet.MonitorServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonWebAutoConfiguration {

    private Logger log = LoggerFactory.getLogger(CommonAutoConfiguration.class);

    @Bean
    @ConditionalOnClass(name = "javax.servlet.Servlet")
    public ServletRegistrationBean monitorServlet(CommonProperties commonProperties) {
        MonitorConfig config = commonProperties.getMonitor();
        log.info("monitor url={}", config.getUrl());
        return new ServletRegistrationBean<>(new MonitorServlet(), config.getUrl());
    }

    @Bean
    @ConditionalOnClass(name = "javax.servlet.Servlet")
    public ServletRegistrationBean healthServlet(CommonProperties commonProperties) {
        HealthCheckConfig config = commonProperties.getServer().getHealthCheck();
        log.info("healthCheck url={}", config.getUrl());
        return new ServletRegistrationBean<>(new HealthCheckServlet(), config.getUrl());
    }

}
