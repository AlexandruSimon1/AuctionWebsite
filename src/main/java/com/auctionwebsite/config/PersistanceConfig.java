package com.auctionwebsite.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("application-${spring.profiles.active}.properties")
public class PersistanceConfig {
    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.driver}")
    private String driverDatabase;

    @Value("${spring.liquibase.change-log}")
    private String liquibaseChangeLog;

    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create().driverClassName(driverDatabase)
                .url(databaseUrl)
                .username(username).password(password).build();
    }

    @Bean
    public SpringLiquibase getSpringLiquibase () {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setChangeLog(liquibaseChangeLog);
        springLiquibase.setDataSource(getDataSource());
        return springLiquibase;
    }
}
