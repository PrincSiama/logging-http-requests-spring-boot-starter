package dev.sosnovsky.configuration;

import dev.sosnovsky.interceptor.LoggingInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
@EnableConfigurationProperties(LoggingStarterProperties.class)
@ConditionalOnProperty(prefix = "logging.http", value = "enable", havingValue = "true")
public class LoggingStarterAutoConfig {

    @Bean
    public LoggingInterceptor loggingInterceptor(LoggingStarterProperties properties) {
        return new LoggingInterceptor(properties);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(LoggingInterceptor loggingInterceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(loggingInterceptor);
            }
        };
    }
}
