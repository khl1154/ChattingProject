package com.clone.chat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
public class ApiWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${project.properties.angular-path}")
    private String angularPath;

    //cors 관련 전역 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/swagger/v2/api-docs", "/v2/api-docs").setKeepQueryParams(true);
        registry.addRedirectViewController("/swagger/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/swagger/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/swagger/swagger-resources", "/swagger-resources");
    }

    //리소스 패스 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")            .addResourceLocations("/WEB-INF/resources/");
        registry.addResourceHandler("/webjars/**")              .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger/**")          	.addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/assets/**")	            .addResourceLocations(angularPath+"/assets/");
        registry.addResourceHandler("/favicon.ico")	        .addResourceLocations(angularPath+"/favicon.ico");
        registry.addResourceHandler("/*.html")	                .addResourceLocations(angularPath+"/");
        registry.addResourceHandler("/*.map")	                .addResourceLocations(angularPath+"/");
        registry.addResourceHandler("/*.js")	                .addResourceLocations(angularPath+"/");
        registry.addResourceHandler("/*.css")	                .addResourceLocations(angularPath+"/");
    }

}
