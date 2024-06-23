package dev.sosnovsky;

import dev.sosnovsky.configuration.LoggingStarterProperties;
import dev.sosnovsky.interceptor.LoggingInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "logging.http.enable=true",
        "logging.http.level=INFO"
})
class LoggingHttpRequestsSpringBootStarterTests {

    @Autowired
    private LoggingInterceptor interceptor;
    @Autowired
    private LoggingStarterProperties properties;
    private WebMvcConfigurer configurer = new WebMvcConfigurer() {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(interceptor);
        }
    };

    @Test
    @DisplayName("Проверка контекста")
    public void contextLoadsTest() {
        assertNotNull(interceptor);
        assertNotNull(properties);
        assertTrue(properties.isEnable());
        assertEquals("INFO", properties.getLevel());
    }

    @Test
    @DisplayName("Логирование http запроса и ответа")
    public void loggingRequestAndResponseTest() {
// todo тесты и обработка ошибок
    }

}
