package com.chenyi.yanhuohui.batchDemo;

import com.chenyi.yanhuohui.DefaultJobParameters;
import com.chenyi.yanhuohui.client.Client;
import com.chenyi.yanhuohui.manager.Manager;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.query.Jpa21Utils;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableBatchProcessing
public class BatchDemoConfiguration {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    @StepScope
    public FlatFileItemReader<Manager> step1Reader() {
        FlatFileItemReader<Manager> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("manager.csv"));
        reader.setLineMapper(new DefaultLineMapper<Manager>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("id", "name", "role");
                    }
                });
                setFieldSetMapper(new FieldSetMapper<Manager>() {
                                      @Override
                                      public Manager mapFieldSet(FieldSet fieldSet) {
                                          Manager manager = new Manager();
                                          //manager.setId(fieldSet.readLong("id"));
                                          manager.setName(fieldSet.readString("name"));
                                          manager.setRole(fieldSet.readString("role"));
                                          return manager;
                                      }
                                  }

                );
                afterPropertiesSet();
            }
        });
        return reader;

    }

    @Bean
    @StepScope
    public ItemProcessor<Manager, Manager> step1Processor() {
        //????????????????????????ItemProcessor?????????CsvItemProcessor
        BatchDemoProcessor processor = new BatchDemoProcessor();
        //???processor??????????????????CsvBeanValidator()
        processor.setValidator(batchDemoValidator());
        return processor;
    }

    @Bean
    @StepScope
    public ItemProcessor<Manager, Client> importCsvStep2Processor(){
        importCsvStep2Processor importCsvStep2Processor = new importCsvStep2Processor();
        return importCsvStep2Processor;
    }

    @Bean
    @StepScope
    public ItemWriter<Manager> step1Writer(@Qualifier("dataSource") DataSource dataSource) throws InterruptedException {
        JdbcBatchItemWriter<Manager> writer = new JdbcBatchItemWriter<>();
        //????????????JDBC????????????JdbcBatchItemWriter????????????????????????
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        String sql = "insert into manager " + " (name,role) "
                + " values(:name,:role)";
        //?????????????????????????????????SQL??????
        writer.setSql(sql);
        writer.setDataSource(dataSource);
        TimeUnit.SECONDS.sleep(5);
        return writer;
    }

    @Bean
    public Job importCsvJob(JobBuilderFactory jobBuilderFactory, @Qualifier("importCsvStep1") Step importCsvStep1,
                            @Qualifier("importCsvStep2") Step importCsvStep2) {
        return jobBuilderFactory.get("importCsvJob")
                //?????????????????????????????????RunIdIncrementer???jobLauncher??????jobParameters?????????
                .incrementer(new RunIdIncrementer())
                .start(importCsvStep2)//start????????????SimpleJobBuilder????????????end,flow?????????
                //.next(importCsvStep2)
                .listener(batchDemoJobListener())
                .build();
    }

    @Bean
    public Step importCsvStep1(StepBuilderFactory stepBuilderFactory, ItemReader<Manager> step1Reader, ItemWriter<Manager> step1Writer,
                      ItemProcessor<Manager, Manager> step1Processor) {
        return stepBuilderFactory.get("importCsvStep1")
                .listener(batchDemoStep1Listener())
                .<Manager, Manager>chunk(1000)//?????????????????????1000?????????
                .reader(step1Reader)//???step??????reader
                .processor(step1Processor)//???step??????processor
                .writer(step1Writer)//???step??????writer
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Manager> importCsvStep2Reader(@Value("#{jobParameters[cut]}") String startTime){
        return new JpaPagingItemReaderBuilder<Manager>().name("importCsvStep2Reader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from Manager c where create_time < '" + startTime + "'")
                .pageSize(3)
                .build();
    }


    @Bean
    @StepScope
    public JpaItemWriter<Client> importCsvStep2Writer() throws Exception {
        //?????????????????????processor????????????????????????????????????????????????
        JpaItemWriter<Client> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }


    @Bean
    public Step importCsvStep2(StepBuilderFactory stepBuilderFactory, ItemReader<Manager> importCsvStep2Reader,
                               ItemWriter<Client> importCsvStep2Writer){
        return stepBuilderFactory.get("importCsvStep2")
                .<Manager,Client>chunk(3)
                .reader(importCsvStep2Reader)
                .processor(importCsvStep2Processor())
                .writer(importCsvStep2Writer)
                .build();
    }

    @Bean
    public BatchDemoJobListener batchDemoJobListener() {
        return new BatchDemoJobListener();
    }

    @Bean
    public BatchDemoStep1Listener batchDemoStep1Listener() {
        return new BatchDemoStep1Listener();
    }

    @Bean
    public Validator<Manager> batchDemoValidator() {
        return new BatchDemoValidator<Manager>();
    }
}
