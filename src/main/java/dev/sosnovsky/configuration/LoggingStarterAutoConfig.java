package dev.sosnovsky.configuration;

import dev.sosnovsky.interceptor.LoggingInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(LoggingStarterProperties.class)
//@ConditionalOnProperty(prefix = "logging.http", value = "enable", havingValue = "true")
public class LoggingStarterAutoConfig {

    @Bean
//    @ConditionalOnProperty(prefix = "logging.http", value = "level")
    public LoggingInterceptor loggingInterceptor(LoggingStarterProperties properties) {
        return new LoggingInterceptor(properties.getLevel());
    }

    @Bean
    public WebConfig webConfig(LoggingInterceptor loggingInterceptor) {
        return new WebConfig(loggingInterceptor);
    }
}
