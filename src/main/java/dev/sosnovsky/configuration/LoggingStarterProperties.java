package dev.sosnovsky.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "logging.http")
public class LoggingStarterProperties {

    private boolean enable;
    private String level = "INFO";
    private boolean headers = true;
}
