package dev.sosnovsky.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Data
@Component
@Primary
@ConfigurationProperties(prefix = "logging.http")
public class LoggingStarterProperties {

    private boolean enable;
    private String level = "INFO";
}
