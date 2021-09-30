package com.epam.esm;

import com.epam.esm.pool.BasicHikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan("com.epam.esm")
public class PersistenceConfig {
    @Bean
    public static JdbcTemplate getTemplate() {
        return new JdbcTemplate(BasicHikariDataSource.getDataSource());
    }
}