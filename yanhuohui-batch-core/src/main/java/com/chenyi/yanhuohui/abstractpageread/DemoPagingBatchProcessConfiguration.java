package com.chenyi.yanhuohui.abstractpageread;

import com.chenyi.yanhuohui.primary.manager.Manager;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

/**
 * @Classname DemoPagingBatchProcessConfiguration
 * @Description TODO
 * @Date 2025/1/9 20:11
 * @Created by 陈义
 */
@Configuration
public class DemoPagingBatchProcessConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public static final String JOB_PAGING_DEMO = "job4PagingDemo";

    public static final String STEP_PAGING_DEMO = "step4PagingDemo";

    /**
     * 配置 Job
     *
     * @return
     */
    @Bean
    public Job demoPageItemJob(@Qualifier("demoPageItemStep") Step step) {
        return this.jobBuilderFactory.get("demoPageItemJob")
                //.incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step demoPageItemStep(@Qualifier("demoPageItemReader") ItemReader demoPageItemReader
            , @Qualifier("demoPagingItemWriter") ItemWriter demoPagingItemWriter
            , @Qualifier("demoPagingItemProcessor")ItemProcessor demoPagingItemProcessor
            , @Qualifier("primaryPlatformTransactionManager") PlatformTransactionManager primaryPlatformTransactionManager) {
        return this.stepBuilderFactory.get("demoPageItemStep")
                .transactionManager(primaryPlatformTransactionManager)
                .chunk(5)
                .reader(demoPageItemReader)
                .processor(demoPagingItemProcessor)
                .writer(demoPagingItemWriter)
                .build();

    }

    @Bean
    @StepScope
    DemoPagingItemReader demoPageItemReader() {
        // pageSize 为 3（每页取 3 条数据）
        return new DemoPagingItemReader(3);
    }

    @Bean
    DemoPagingItemWriter demoPagingItemWriter() {
        return new DemoPagingItemWriter();
    }

    @Bean
    public ItemProcessor<Manager, Manager> demoPagingItemProcessor() {
        return new DemoPagingItemProcessor();
    }
}
