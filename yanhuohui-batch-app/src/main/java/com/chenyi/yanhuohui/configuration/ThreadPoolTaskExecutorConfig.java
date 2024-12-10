package com.chenyi.yanhuohui.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Classname ThreadPoolTaskExecutorConfig
 * @Description TODO
 * @Date 2024/11/18 10:25
 * @Created by 陈义
 */
@Configuration
public class ThreadPoolTaskExecutorConfig {
    @Bean("yanHuoHuiBatchtaskExecutor")
    public ThreadPoolTaskExecutor schedulingTaskExecutor() {
        return initExecutor(4, 4, 1, new ThreadPoolExecutor.AbortPolicy(), "YanHuoHuiBatch-taskExecutor-");
    }

    private ThreadPoolTaskExecutor initExecutor(int corePoolSize, int maxPoolSize, int queueCapacity, RejectedExecutionHandler rejectedExecutionHandler, String threadNamePrefix) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(60);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(rejectedExecutionHandler);
        executor.setTaskDecorator(new ContextCopyingDecorator());
        return executor;
    }
}
