package com.epam.esm.config;

import com.epam.esm.PersistenceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
@Import(PersistenceConfig.class)
public class WebConfig {
}