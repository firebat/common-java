package common.jdbc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonJdbcConfiguration {

    @Bean
    public ShardKeyAspect shardKeyAspect() {
        return new ShardKeyAspect();
    }
}
