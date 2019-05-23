package common.autoconfigure;

import common.management.ServerLifecycle;
import common.management.ServerManager;
import common.servlet.EnvironmentServlet;
import common.servlet.HealthServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class CommonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ServerConfig serverConfig() {
        return new ServerConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    public HealthConfig healthConfig() {
        return new HealthConfig();
    }

    @Bean
    @ConditionalOnClass(name = "javax.servlet.http.HttpServlet")
    public ServletRegistrationBean environmentServlet(ServerConfig serverConfig) {
        return new ServletRegistrationBean<>(new EnvironmentServlet(), serverConfig.getEnv());
    }

    @Bean
    @ConditionalOnClass(name = "javax.servlet.http.HttpServlet")
    public ServletRegistrationBean healthServlet(HealthConfig healthConfig) {
        return new ServletRegistrationBean<>(new HealthServlet(), healthConfig.getUrl());
    }

    @Bean
    public SmartLifecycle serverLifecycle(ServerConfig serverConfig,
                                          HealthConfig healthConfig) {

        return new ServerLifecycle(ServerManager.get(), serverConfig, healthConfig);
    }
}
