package common.rpc.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class RestAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RestProperties restProperties() {
        return new RestProperties();
    }
}
