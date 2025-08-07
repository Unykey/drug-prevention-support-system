package com.swp08.dpss.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // Only allow requests from localhost frontend
                .allowedOrigins("https://drug-prevention-support-system.vercel.app")
                .allowedMethods("*") // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
                .allowedHeaders("*") // Allow all headers (e.g., Content-Type, Authorization)
                .allowCredentials(true); // Allow sending cookies / Authorization headers
    }

    // If a file already exists in the uploads/ directory on the server, then when someone accesses /images/{filename}, serve that file.
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploads");
    }
}