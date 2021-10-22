package com.epam.esm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackages = {"com.epam.esm.*"})
public class SpringInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SpringInitializer.class, args);
    }
}
