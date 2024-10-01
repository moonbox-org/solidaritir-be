package com.moonboxorg.solidaritirbe.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Slf4j
@Configuration
public class WebConfig {

    private static final String EMPTY_STR = "";

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CustomRequestLoggingFilter loggingFilter = new CustomRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(10000);
        loggingFilter.setBeforeMessagePrefix(EMPTY_STR);
        loggingFilter.setAfterMessagePrefix(EMPTY_STR);
        return loggingFilter;
    }
}
