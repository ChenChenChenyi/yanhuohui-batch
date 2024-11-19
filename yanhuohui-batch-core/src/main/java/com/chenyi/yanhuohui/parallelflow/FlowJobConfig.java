package com.chenyi.yanhuohui.parallelflow;

import com.chenyi.yanhuohui.csvfile.CsvJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Classname FlowJobConfig
 * @Description TODO
 * @Date 2024/11/18 10:30
 * @Created by 陈义
 */
@Configuration
public class FlowJobConfig {

    @Bean
    public Step parallelFlowStep1(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("parallelFlowStep1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(Thread.currentThread().getName() + ": Executing parallelFlowStep1");
                    Thread.sleep(5000);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step parallelFlowStep2(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("parallelFlowStep2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(Thread.currentThread().getName() + ": Executing parallelFlowStep2");
                    //throw new RuntimeException("抛出的异常");
                    Thread.sleep(5000);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step parallelFlowStep3(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("parallelFlowStep3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(Thread.currentThread().getName() + ": Executing parallelFlowStep3");
                    Thread.sleep(5000);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


    @Bean
    public Flow parallelFlowflowA(@Qualifier("parallelFlowStep1")Step step1, @Qualifier("parallelFlowStep2")Step step2) {
        return new FlowBuilder<SimpleFlow>("parallelFlowflowA")
                .start(step1)
                .next(step2)
                .build();
    }

    @Bean
    public Flow parallelFlowflowB(@Qualifier("parallelFlowStep3") Step step3) {
        return new FlowBuilder<SimpleFlow>("parallelFlowflowB")
                .start(step3)
                .build();
    }

    @Bean
    public Flow parallelFlowsplitFlow(@Qualifier("YanHuoHuiBatchtaskExecutor") TaskExecutor taskExecutor,@Qualifier("parallelFlowflowA")Flow flowA,@Qualifier("parallelFlowflowB")Flow flowB) {
        return new FlowBuilder<SimpleFlow>("parallelFlowsplitFlow")
                .split(taskExecutor)
                .add(flowA, flowB)
                .build();
    }

//    @Bean
//    public Job job(JobRepository jobRepository, @Qualifier("parallelFlowsplitFlow")Flow splitFlow) {
//        return new JobBuilder("job", jobRepository)
//                .start(flow1)
//                .split(new SimpleAsyncTaskExecutor())
//                .add(flow2)
//                .end()
//                .build();
//    }

    @Bean
    public Job parallelFlowsplitJob(JobBuilderFactory jobBuilderFactory, @Qualifier("parallelFlowsplitFlow")Flow splitFlow) {
        return jobBuilderFactory.get("parallelFlowsplitJob")
                .listener(paralellStepExecutionListener())
                .start(splitFlow)
                .build()        //builds FlowJobBuilder instance
                .build();       //builds Job instance
    }

    @Bean
    public ParalellStepExecutionListener paralellStepExecutionListener() {
        return new ParalellStepExecutionListener();
    }
}
