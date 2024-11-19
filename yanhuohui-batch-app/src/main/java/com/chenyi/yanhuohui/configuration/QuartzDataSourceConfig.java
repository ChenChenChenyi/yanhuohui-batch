//package com.chenyi.yanhuohui.configuration;
//
//import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
//import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.jdbc.datasource.init.DataSourceInitializer;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//
//import javax.sql.DataSource;
//
///**
// * @Classname QuartzDataSourceConfig
// * @Description TODO
// * @Date 2024/11/19 11:46
// * @Created by 陈义
// */
//@Configuration
//public class QuartzDataSourceConfig {
//    @QuartzDataSource
//    DataSource dataSource;
//
//    public QuartzDataSourceConfig(DataSource dataSource){
//        this.dataSource = dataSource;
//    }
//
//
//    @Bean
//    public DataSourceInitializer databasePopulator() {
//        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//        //populator.addScript(new ClassPathResource("org/springframework/batch/core/schema-h2.sql"));
//        populator.setContinueOnError(false);
//        populator.setIgnoreFailedDrops(false);
//        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
//        dataSourceInitializer.setDataSource(dataSource);
//        dataSourceInitializer.setDatabasePopulator(populator);
//        return dataSourceInitializer;
//    }
//}
