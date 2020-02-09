package com.documentflow.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * App configuration
 * */
@Configuration
@ComponentScan(basePackages = {"com.documentflow.entities",
        "com.documentflow.repositories",
        "com.documentflow.services",
        "com.documentflow.controllers"})
public class AppConfig {
}
