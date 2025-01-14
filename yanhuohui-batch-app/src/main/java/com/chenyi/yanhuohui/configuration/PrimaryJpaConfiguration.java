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
 * @Description @EnableJpaRepositories注解属性的详解：
 * 1.basePackages:会在这个包及其子包中查找接口并自动为它们生成实现类
 * 2.entityManagerFactoryRef用于指定要使用的 EntityManagerFactory 的 bean 名称,
 *   EntityManagerFactory 是 JPA 的核心组件之一，它负责创建 EntityManager，并提供了与数据库交互的功能。
 *   我猜测basePackages里面扫描到的接口就是通过这个EntityManagerFactory生成的EntityManager生成的实现类
 * 3.transactionManagerRef用于指定事务管理器的引用，每个数据源都需要一个transactionManager
 *
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
