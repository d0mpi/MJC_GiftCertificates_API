package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@ComponentScan("com.epam.esm")
@AllArgsConstructor
@EntityScan(basePackages = {"com.epam.esm.*"})
public class PersistenceConfig {

    private Environment environment;

    @Bean
    public static DataSource getDataSource() {
        return new HikariDataSource(new HikariConfig("/database.properties"));
    }

    @Bean
    public static JdbcTemplate getTemplate() {
        return new JdbcTemplate(getDataSource());
    }


    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan("com.epam.esm");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("spring.jpa.show-sql",
                environment.getProperty("spring.jpa.show-sql"));
        return properties;
    }
}