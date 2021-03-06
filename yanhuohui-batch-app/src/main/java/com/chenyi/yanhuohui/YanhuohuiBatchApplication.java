package com.chenyi.yanhuohui;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableBatchProcessing
@EnableDiscoveryClient
@EnableFeignClients
public class YanhuohuiBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(YanhuohuiBatchApplication.class, args);
    }
}
