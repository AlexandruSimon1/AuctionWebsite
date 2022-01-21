package com.auctionwebsite.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@PropertySource("application-${spring.profiles.active}.properties")
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${ui.url.origin}")
    private String myAllowedApi;

    @Value("${ui.github.origin}")
    private String gitHubIO;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/auction-system-api/**")
                .allowedOrigins(gitHubIO, myAllowedApi)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .maxAge(3600)
                .allowCredentials(true);
    }
}
