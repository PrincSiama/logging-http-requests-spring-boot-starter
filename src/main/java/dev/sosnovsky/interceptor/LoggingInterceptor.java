package dev.sosnovsky.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    private String level;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        request.setAttribute("startTime", Instant.now());
        log.atLevel(Level.valueOf(level)).log("Запрос: Метод: {}, URL: {}, Заголовки: {}",
                request.getMethod(), request.getRequestURL(), getHeaders(request));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        Instant startTime = (Instant) request.getAttribute("startTime");
        long duration = Instant.now().toEpochMilli() - startTime.toEpochMilli();
        log.atLevel(Level.valueOf(level))
                .log("Ответ: Статус: {}, Время выполнения: {} ms, Заголовки: {}",
                        response.getStatus(), duration, getHeaders(response));
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
