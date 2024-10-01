package com.moonboxorg.solidaritirbe.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Slf4j
public class CustomRequestLoggingFilter extends CommonsRequestLoggingFilter {

    private static final String INCOMING_REQUEST = "Incoming Request: ";
    private static final String COMPLETED_REQUEST = "Completed Response: ";
    private static final String ACTUATOR_ENDPOINT = "/actuator";

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        if (shouldLog(request)) {
            log.info("{}{}", INCOMING_REQUEST, message);
        }
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        if (shouldLog(request)) {
            log.info("{}{}", COMPLETED_REQUEST, message);
        }
    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return !uri.startsWith(ACTUATOR_ENDPOINT);
    }
}
