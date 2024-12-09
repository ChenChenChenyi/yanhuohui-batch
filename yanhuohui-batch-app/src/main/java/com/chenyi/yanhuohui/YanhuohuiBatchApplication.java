package com.chenyi.yanhuohui;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableBatchProcessing
@EnableAsync
public class YanhuohuiBatchApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(YanhuohuiBatchApplication.class, args);
    }
}
