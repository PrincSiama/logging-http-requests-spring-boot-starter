package dev.sosnovsky;

import dev.sosnovsky.configuration.LoggingStarterAutoConfig;
import dev.sosnovsky.interceptor.LoggingInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.assertj.core.api.Assertions.assertThat;

class LoggingStarterAutoConfigTest {

    private final ApplicationContextRunner applicationContextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(LoggingStarterAutoConfig.class));

    @Test
    @DisplayName("Создание бинов при параметре logging.http.enable=true")
    public void createBeansWhenEnable_true() {
        applicationContextRunner
                .withPropertyValues("logging.http.enable=true", "logging.http.level=INFO")
                .run(context -> {
                    assertThat(context).hasSingleBean(LoggingInterceptor.class);
                    assertThat(context).hasSingleBean(WebMvcConfigurer.class);
                });
    }

    @Test
    @DisplayName("Создание бинов при параметре logging.http.enable=false")
    public void createBeansWhenEnable_false() {
        applicationContextRunner
                .withPropertyValues("logging.http.enable=false", "logging.http.level=INFO")
                .run(context -> {
                    assertThat(context).doesNotHaveBean(LoggingInterceptor.class);
                    assertThat(context).doesNotHaveBean(WebMvcConfigurer.class);
                });
    }

    @Test
    @DisplayName("Создание бинов при отсутствующем параметре logging.http.enable")
    public void createBeansWhenEnableIsAbsent() {
        applicationContextRunner
                .run(context -> {
                    assertThat(context).doesNotHaveBean(LoggingInterceptor.class);
                    assertThat(context).doesNotHaveBean(WebMvcConfigurer.class);
                });
    }
}