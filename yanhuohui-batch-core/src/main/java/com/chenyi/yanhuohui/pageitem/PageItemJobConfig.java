package com.chenyi.yanhuohui.pageitem;

import com.chenyi.yanhuohui.manager.Manager;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname PageItemJob
 * @Description TODO
 * @Date 2024/11/19 18:39
 * @Created by 陈义
 */
@Configuration
public class PageItemJobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcPagingItemReader<Manager> jdbcPagingItemReader() {
        JdbcPagingItemReader<Manager> pagingItemReader = new JdbcPagingItemReader<>();

        pagingItemReader.setDataSource(dataSource);
        pagingItemReader.setFetchSize(5);
        pagingItemReader.setRowMapper(new ManagerRowMapper());

        MySqlPagingQueryProvider mySqlPagingQueryProvider = new MySqlPagingQueryProvider();
        mySqlPagingQueryProvider.setSelectClause("id, name, role, create_time");
        mySqlPagingQueryProvider.setFromClause("FROM manager");

        Map<String, Order> orderByKeys = new HashMap<>();
        orderByKeys.put("id", Order.ASCENDING);

        mySqlPagingQueryProvider.setSortKeys(orderByKeys);

        pagingItemReader.setQueryProvider(mySqlPagingQueryProvider);

        return pagingItemReader;
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
    public Step pageItemStep() {
        return this.stepBuilderFactory.get("pageItemStep").chunk(5).reader(jdbcPagingItemReader()).writer(itemWriter())
                .build();
    }

    @Bean
    public Job pageItemJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {

        return jobBuilderFactory.get("pageItemJob").start(pageItemStep()).build();
    }
}
