package dev.sosnovsky;

import dev.sosnovsky.configuration.LoggingStarterProperties;
import dev.sosnovsky.interceptor.LoggingInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class LoggingInterceptorTest {

    private LoggingInterceptor interceptor;
    private LoggingStarterProperties properties;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private Object handler;

    @BeforeEach
    public void setUp() {
        properties = new LoggingStarterProperties();
        properties.setEnable(true);
        properties.setLevel("INFO");
        interceptor = new LoggingInterceptor(properties);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        handler = new Object();
    }

    @Test
    @DisplayName("Проверка работы метода preHandle")
    void preHandleTest() {
        boolean result = interceptor.preHandle(request, response, handler);
        assertTrue(result);
        assertNotNull(request.getAttribute("startTime"));
    }

    @Test
    @DisplayName("Логирование http запроса и ответа")
    public void loggingRequestAndResponseTest() {
        request.setMethod("GET");
        request.setRequestURI("http://localhost:8080/cars");
        request.addHeader("User-Agent", "test");

        response.setStatus(200);
        response.addHeader("Content-Type", "application/json");

//        verify(properties.getLevel(), times(1)).;
    }
}