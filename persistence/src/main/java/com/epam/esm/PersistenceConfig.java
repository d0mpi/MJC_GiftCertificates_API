package com.epam.esm;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
public class PersistenceConfig {
    @Bean
    public static DataSource getDataSource() {
        return new HikariDataSource(new HikariConfig("/dev/database.properties"));
    }

    @Bean
    public static JdbcTemplate getTemplate() {
        return new JdbcTemplate(getDataSource());
    }
}