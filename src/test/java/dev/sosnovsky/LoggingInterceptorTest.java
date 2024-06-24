package dev.sosnovsky;

import dev.sosnovsky.configuration.LoggingStarterProperties;
import dev.sosnovsky.interceptor.LoggingInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoggingInterceptorTest {

    private LoggingInterceptor interceptor;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private Object handler;

    @BeforeEach
    public void setUp() {
        LoggingStarterProperties properties = new LoggingStarterProperties();
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
    @DisplayName("Проверка корректной работы метода preHandle при возникновении ошибки")
    void preHandleWithExceptionTest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeaderNames()).thenThrow(RuntimeException.class);
        boolean result = interceptor.preHandle(request, response, handler);
        assertTrue(result);
    }
}