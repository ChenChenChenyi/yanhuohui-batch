package com.chenyi.yanhuohui.csvfile;

import com.chenyi.yanhuohui.primary.manager.Manager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

/**
 * @Classname CsvBatchConfig
 * @Description TODO
 * @Date 2024/11/12 18:22
 * @Created by 陈义
 */
@Configuration
@Slf4j
public class CsvBatchConfig {
    /** 说明：FlatFileItemReader可以读取任何以换行符分隔的文本文件（例如 .txt 或 .csv 文件）
     * ItemReader定义,用来读取数据
     * 1，使用FlatFileItemReader读取文件
     * 2，使用FlatFileItemReader的setResource方法设置csv文件的路径
     * 3，对此对cvs文件的数据和领域模型类做对应映射
     *
     * @return FlatFileItemReader
     */
    @Bean
    @StepScope
    public FlatFileItemReader<Manager> reader(@Value("#{jobParameters[filename]}") String pathToFile) {
        FlatFileItemReader<Manager> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(pathToFile));

        DefaultLineMapper<Manager> lineMapper = new DefaultLineMapper<>();
        // 配置LineTokenizer
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        //lineTokenizer.setNames("id", "name", "role");
        lineTokenizer.setNames("name", "role");
        lineMapper.setLineTokenizer(lineTokenizer);
        // 自定义FieldSetMapper
        lineMapper.setFieldSetMapper(new FieldSetMapper<Manager>() {
            @Override
            public Manager mapFieldSet(FieldSet fieldSet) {
                Manager data = new Manager();
//                //UTF-8-BOM编码格式的CSV文件开头有一个UFEFF字符，需要去掉
//                String id = fieldSet.readString("id").trim();
//                String UTF8_BOM="\uFEFF";
//                if(id.startsWith(UTF8_BOM)) {
//                    id=id.substring(1);
//                }
//                long l = Long.parseLong(id);
//                data.setId(l);        // 字符串转换为int
                data.setName(fieldSet.readString("name"));     // 字符串转换为double
                data.setRole(fieldSet.readString("role"));     // 字符串
                return data;
            }
        });
        reader.setLineMapper(lineMapper);
        return reader;
    }

    /**
     * ItemProcessor定义，用来处理数据
     *
     * @return
     */
    @Bean
    public ItemProcessor<Manager, Manager> processor() {
        //使用我们自定义的ItemProcessor的实现CsvItemProcessor
        CsvItemProcessor processor = new CsvItemProcessor();
        //为processor指定校验器为CsvBeanValidator()
        processor.setValidator(csvBeanValidator());
        return processor;
    }

    /**
     * ItemWriter定义，用来输出数据
     * spring能让容器中已有的Bean以参数的形式注入，Spring Boot已经为我们定义了dataSource
     *
     * @param dataSource
     * @return
     */
    @Bean
    public ItemWriter<Manager> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Manager> writer = new JdbcBatchItemWriter<>();
        //我们使用JDBC批处理的JdbcBatchItemWriter来写数据到数据库
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());

        String sql = "insert into manager " + " (name,role,create_time) "
                + " values(:name,:role,:createTime)";
        //在此设置要执行批处理的SQL语句
        writer.setSql(sql);
        writer.setDataSource(dataSource);
        return writer;
    }

    /**
     * Job定义，我们要实际执行的任务，包含一个或多个Step
     *
     * @param jobBuilderFactory
     * @param s1
     * @return
     */
    @Bean
    public Job csvFileimportJob(JobBuilderFactory jobBuilderFactory,@Qualifier("csvFileimportStep") Step s1) {
        return jobBuilderFactory.get("csvFileimportJob")
                .incrementer(new RunIdIncrementer())
                .flow(s1)//为Job指定Step
                .end()
                .listener(csvJobListener())//绑定监听器csvJobListener
                .build();
    }


    /**
     * step步骤，包含ItemReader，ItemProcessor和ItemWriter
     *
     * @param stepBuilderFactory
     * @param reader
     * @param writer
     * @param processor
     * @return
     */
    @Bean
    public Step csvFileimportStep(StepBuilderFactory stepBuilderFactory, ItemReader<Manager> reader, ItemWriter<Manager> writer,
                      ItemProcessor<Manager, Manager> processor) {
        return stepBuilderFactory
                .get("csvFileimportStep")
                .<Manager, Manager>chunk(3)//批处理每次提交3条数据
                .reader(reader)//给step绑定reader
                .processor(processor)//给step绑定processor
                .writer(writer)//给step绑定writer
                .listener(new CsvFileReaderListener())
                .listener(new CsvFileWriterListener())
                .stream(stream())
                .build();
    }


    @Bean
    public CsvJobListener csvJobListener() {
        return new CsvJobListener();
    }

    @Bean
    public Validator<Manager> csvBeanValidator() {
        return new CsvBeanValidator<Manager>();
    }

    @Bean
    public ItemCountItemStream stream() {
        return new ItemCountItemStream();
    }


}
