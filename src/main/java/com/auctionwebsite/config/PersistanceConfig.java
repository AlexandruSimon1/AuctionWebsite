package com.auctionwebsite.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PersistanceConfig {
    //define this property in your embedded properties file or use spring's default
    @Value("${liquibase.change-log}")
    private String defaultLiquibaseChangelog;
    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create().driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/auction_db?serverTimezone=UTC")
                .username("root").password("Sanea@21041990").build();
    }

    @Bean
    public SpringLiquibase getSpringLiquiBase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(getDataSource());
        liquibase.setChangeLog(defaultLiquibaseChangelog);
        return liquibase;
    }
}
