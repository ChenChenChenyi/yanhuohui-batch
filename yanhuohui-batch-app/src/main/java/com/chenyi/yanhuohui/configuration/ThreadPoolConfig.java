//package com.chenyi.yanhuohui.configuration;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.ThreadPoolExecutor;
//
///**
// * @Classname ThreadPoolConfig
// * @Description TODO
// * @Date 2024/11/13 16:35
// * @Created by 陈义
// */
//@Configuration
//@EnableAsync
//@Slf4j
//public class ThreadPoolConfig {
//
//    //自定义使用参数
//    @Value("${async.executor.thread.core_pool_size}")
//    private int corePoolSize;   //配置核心线程数
//    @Value("${async.executor.thread.max_pool_size}")
//    private int maxPoolSize;    //配置最大线程数
//    @Value("${async.executor.thread.queue_capacity}")
//    private int queueCapacity;
//    @Value("${async.executor.thread.name.prefix}")
//    private String namePrefix;
//    @Value("${async.executor.thread.keep_alive_seconds}")
//    private int keepAliveSeconds;
//
//    /**
//     1.自定义asyncServieExecutor线程池
//     */
//    @Bean(name = "asyncServiceExecutor")
//    public ThreadPoolTaskExecutor asyncServiceExecutor(){
//
//        log.info("start asyncServiceExecutor......");
//
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        //配置核心线程数
//        executor.setCorePoolSize(corePoolSize);
//        //配置最大线程数
//        executor.setMaxPoolSize(maxPoolSize);
//        //设置线程空闲等待时间 s
//        executor.setKeepAliveSeconds(keepAliveSeconds);
//        //配置队列大小 设置任务等待队列的大小
//        executor.setQueueCapacity(queueCapacity);
//        //配置线程池中的线程的名称前缀
//        //设置线程池内线程名称的前缀-------阿里编码规约推荐--方便出错后进行调试
//        executor.setThreadNamePrefix(namePrefix);
//
//        /**
//         rejection-policy：当pool已经达到max size的时候，如何处理新任务
//         CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
//         */
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
//
//        //执行初始化
//        executor.initialize();
//        return executor;
//    }
//
//}
