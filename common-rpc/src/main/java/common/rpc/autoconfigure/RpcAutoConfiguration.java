package common.rpc.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class RpcAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RpcProperties rpcProperties() {
        return new RpcProperties();
    }
}
