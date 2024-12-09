package com.chenyi.yanhuohui.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        basePackages = "com.chenyi.yanhuohui.wangdai.*",
        entityManagerFactoryRef = "wangdaiEntityManagerFactory",
        transactionManagerRef = "wangdaiPlatformTransactionManager"
)
public class WangdaiJpaConfiguration {
    @Bean
    public LocalContainerEntityManagerFactoryBean wangdaiEntityManagerFactory(
            @Qualifier("wangdaiDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .packages("com.chenyi.yanhuohui.wangdai.*")
                .build();
    }

    @Bean("wangdaiPlatformTransactionManager")
    public PlatformTransactionManager wangdaiTransactionManager(
            @Qualifier("wangdaiEntityManagerFactory") LocalContainerEntityManagerFactoryBean wangdaiEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(wangdaiEntityManagerFactory.getObject()));
    }
}
