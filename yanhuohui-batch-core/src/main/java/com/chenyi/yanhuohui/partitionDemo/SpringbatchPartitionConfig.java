package com.chenyi.yanhuohui.partitionDemo;

import com.chenyi.yanhuohui.client.Client;
import com.chenyi.yanhuohui.manager.Manager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.persistence.ColumnResult;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@Slf4j
public class SpringbatchPartitionConfig {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private stepExecutionPartitionListener stepExecutionPartitionListener;
    @Autowired
    private ReadByPagePartition readByPagePartition;

    @Autowired
    @Qualifier("PartitionSlaveReader")
    private ItemReader<? extends Manager> partitionSlaveReader;

    @Autowired
    @Qualifier("PartitionSlaveWriter")
    private ItemWriter<? super Manager> partitionSlaveWriter;



    @Bean(name = "partitionerJob")
    public Job partitionerJob() throws Exception {
        return jobBuilderFactory.get("partitionerJob")
                .start(partitionStep())
                .build();
    }

    @Bean
    public Step partitionStep() throws Exception {
        return stepBuilderFactory.get("partitionStep")
                .partitioner("slaveStep", readByPagePartition)
                .partitionHandler(partitionHandler(this.slaveStep(),this.taskExecutor()))
                .step(slaveStep())
                .taskExecutor(taskExecutor())
                .aggregator(stepExecutionAggregator())
                .build();
    }

    @Bean
    public Step slaveStep() throws Exception {
        return stepBuilderFactory.get("slaveStep")
                .<Manager, Manager>chunk(50)
                .reader(partitionSlaveReader)
                .writer(partitionSlaveWriter)
                .listener(stepExecutionPartitionListener)
                .build();
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setQueueCapacity(5);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

    @Bean
    public PartitionHandler partitionHandler(Step slaveStep,ThreadPoolTaskExecutor taskExecutor){
        TaskExecutorPartitionHandler taskExecutorPartitionHandler = new TaskExecutorPartitionHandler();
        taskExecutorPartitionHandler.setTaskExecutor(taskExecutor);
        taskExecutorPartitionHandler.setStep(slaveStep);
        taskExecutorPartitionHandler.setGridSize(2);
        return taskExecutorPartitionHandler;
    }

    @Bean
    public MyStepExecutionAggregator stepExecutionAggregator(){
        return new MyStepExecutionAggregator();
    }


    @Bean
    @StepScope
    public JdbcPagingItemReader<Map<String, Object>> slaveReader(@Value("#{stepExecutionContext['startTime']}") String startTime,
                                                     @Value("#{stepExecutionContext['endTime']}") String endTime){
       Map<String,Order> sortKeys = new HashMap<>(1);
       sortKeys.put("create_time",Order.ASCENDING);
       Map<String, Object> paramValue = new HashMap<>();
       paramValue.put("startTime",startTime);
       paramValue.put("endTime",endTime);

       JdbcPagingItemReaderBuilder<Map<String, Object>> readerBuilder = new JdbcPagingItemReaderBuilder<>();
       readerBuilder.dataSource(dataSource)
               .name("slaveReader")
               .selectClause("select id,name,role,create_time")
               .fromClause("from manager")
               .whereClause("where create_time between :startTime and :endTime")
               .parameterValues(paramValue)
               .sortKeys(sortKeys)
               .rowMapper(new ColumnMapRowMapper())
               .pageSize(2000);
       return readerBuilder.build();

    }

    @Bean
    @StepScope
    public ItemProcessor<Manager,String> slaveProcessor(){
        return new ItemProcessor<Manager, String>() {
            @Override
            public String process(Manager manager) throws Exception {
                return manager.getName();
            }
        };
    }

    @Bean
    @StepScope
    public ItemWriter<String> slaveWriter(){
        return new ItemWriter<String>() {
            @Override
            public void write(List<? extends String> list) throws Exception {
                list.stream().forEach(item->
                        log.info(Thread.currentThread().getName()+"->"+ item));
            }
        };
    }
}
