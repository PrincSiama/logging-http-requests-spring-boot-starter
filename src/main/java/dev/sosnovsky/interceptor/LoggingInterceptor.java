package dev.sosnovsky.interceptor;

import dev.sosnovsky.configuration.LoggingStarterProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    private final LoggingStarterProperties properties;

    private final LoggingEventBuilder loggingEventBuilder;

    public LoggingInterceptor(LoggingStarterProperties properties) {
        this.properties = properties;
        loggingEventBuilder = log.atLevel(Level.valueOf(properties.getLevel()));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        try {
            request.setAttribute("startTime", Instant.now());
            loggingEventBuilder.log(
                    "Запрос: Метод: " + request.getMethod() +
                            ", URL: " + request.getRequestURL());
            if (properties.isHeaders()) {
                loggingEventBuilder.log("Заголовки: " + getHeaders(request));
            }
        } catch (Exception e) {
            log.warn("Не удалось логировать запрос, {}", e.getMessage());
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        try {
            Instant startTime = (Instant) request.getAttribute("startTime");
            long duration = Instant.now().toEpochMilli() - startTime.toEpochMilli();
            loggingEventBuilder.log(
                    "Ответ: Статус: " + response.getStatus() +
                            ", Время выполнения: " + duration + " ms");
            if (properties.isHeaders()) {
                loggingEventBuilder.log("Заголовки: " + getHeaders(response));
            }
        } catch (Exception e) {
            log.warn("Не удалось логировать ответ, {}", e.getMessage());
        }
    }

    private String getHeaders(HttpServletRequest request) {

        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers.toString();
    }

    private String getHeaders(HttpServletResponse response) {
        Map<String, String> headers = new HashMap<>();

        for (String headerName : response.getHeaderNames()) {
            headers.put(headerName, response.getHeader(headerName));
        }
        return headers.toString();
    }
}