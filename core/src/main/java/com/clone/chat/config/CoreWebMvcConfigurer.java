package com.clone.chat.config;

import com.clone.chat.filter.RequestLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CoreWebMvcConfigurer implements WebMvcConfigurer {

    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
        RequestLoggingFilter filter = new RequestLoggingFilter();
        return filter;
    }

    @Bean
    public FilterRegistrationBean commonsRequestLoggingFilter(RequestLoggingFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(5);
        return registrationBean;
    }
}
