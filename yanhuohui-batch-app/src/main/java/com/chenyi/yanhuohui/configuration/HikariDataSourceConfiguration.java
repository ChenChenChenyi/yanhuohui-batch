package com.chenyi.yanhuohui.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @Classname HikariDataSourceConfiguration
 * @Description TODO
 * @Date 2024/12/3 19:06
 * @Created by 陈义
 */
@Configuration
public class HikariDataSourceConfiguration {
    @Primary
    @Bean("primaryDataSourceProperties")
    @ConfigurationProperties("spring.datasource.primary")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean("primaryDataSource")
    @Qualifier(value = "primaryDataSource")
    // 留意下面这行
    @ConfigurationProperties(prefix = "spring.datasource.primary.hikari")
    public HikariDataSource primaryDataSource() {
        return primaryDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean("wangdaiDataSourceProperties")
    @ConfigurationProperties("spring.datasource.wangdai")
    public DataSourceProperties wangdaiDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("wangdaiDataSource")
    @Qualifier(value = "wangdaiDataSource")
    // 留意下面这行
    @ConfigurationProperties(prefix = "spring.datasource.wangdai.hikari")
    public HikariDataSource wangdaiDataSource() {
        return wangdaiDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "wangdaiJdbcTemplate")
    public JdbcTemplate wangdaiJdbcTemplate(@Qualifier("wangdaiDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
