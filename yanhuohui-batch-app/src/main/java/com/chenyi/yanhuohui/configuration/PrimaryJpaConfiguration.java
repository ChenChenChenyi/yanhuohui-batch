package com.chenyi.yanhuohui.configuration;

import com.chenyi.yanhuohui.primary.manager.Manager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * @Classname PrimaryJpaConfiguration
 * @Description TODO
 * @Date 2024/12/6 15:47
 * @Created by 陈义
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.chenyi.yanhuohui.primary.*",
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryPlatformTransactionManager"
)
public class PrimaryJpaConfiguration {
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            @Qualifier("primaryDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .packages("com.chenyi.yanhuohui.primary.*")
                .build();
    }

    @Bean("primaryPlatformTransactionManager")
    @Primary
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("primaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(primaryEntityManagerFactory.getObject()));
    }
}
