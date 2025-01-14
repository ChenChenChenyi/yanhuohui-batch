package com.chenyi.yanhuohui.jpapageread;

import com.chenyi.yanhuohui.jdbcpageread.ManagerRowMapper;
import com.chenyi.yanhuohui.primary.manager.Manager;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Classname JpaPageItemReadConfig
 * @Description TODO
 * @Date 2025/1/9 19:09
 * @Created by 陈义
 */
@Configuration
public class JpaPageItemReadConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public JpaPagingItemReader<Manager> jpaPagingItemReader(@Qualifier("primaryEntityManagerFactory")LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory) {
        JpaPagingItemReader<Manager> jpaPagingItemReader = new JpaPagingItemReader<>();
        jpaPagingItemReader.setName("jpaPagingItemReader");
        jpaPagingItemReader.setEntityManagerFactory(primaryEntityManagerFactory.getObject());
        jpaPagingItemReader.setQueryString("select m from Manager m where name = :name");//注意这里的表名Manager要大写首字母，与实体类保持一致
        Map<String,Object> params = Stream.of(new Object[][] {
                { "name", "chenyi" }
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Object) data[1]));
        jpaPagingItemReader.setParameterValues(params);
        jpaPagingItemReader.setPageSize(5);
        return jpaPagingItemReader;
    }

    @Bean
    public ItemWriter<? super Object> itemWriter() {
        return emps -> {
            System.out.println("\nWriting chunk to console");
            for (Object emp : emps) {
                System.out.println(emp);
            }
        };
    }

    @Bean
    public Step jpaPageItemStep(@Qualifier("jpaPagingItemReader")JpaPagingItemReader jpaPagingItemReader) {
        return this.stepBuilderFactory.get("jpaPageItemStep").chunk(5).reader(jpaPagingItemReader).writer(itemWriter())
                .build();
    }

    @Bean
    public Job jpaPageItemJob(JobRepository jobRepository,@Qualifier("jpaPageItemStep")Step jpaPageItemStep) {

        return jobBuilderFactory.get("jpaPageItemJob").start(jpaPageItemStep).build();
    }
}
