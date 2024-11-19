package com.chenyi.yanhuohui.helloworld;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.transaction.TransactionManager;

/**
 * @Classname HelloWorldJobConfiguration
 * @Description 使用Tasklet实现简单的任务，并用listener收集记录处理统计数据
 * @Date 2024/11/12 14:28
 * @Created by 陈义
 */
@Configuration
public class HelloWorldJobConfiguration {

    @Bean
    public Step helloWorldStep(StepBuilderFactory stepBuilderFactory,@Qualifier("helloWorldTasklet") Tasklet tasklet1) {
        return stepBuilderFactory.get("helloWorldStep")
                .tasklet(tasklet1)
                .listener(new HelloWorldStepListener())
                .build();
    }

    @Bean
    public Job helloWorldJob(JobBuilderFactory jobBuilderFactory,@Qualifier("helloWorldStep")Step s1) {
        return jobBuilderFactory.get("helloWorldJob")
                .start(s1)
                .build();
    }
}
