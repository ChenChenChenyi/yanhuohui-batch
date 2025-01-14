package com.chenyi.yanhuohui.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @Classname PageItemController
 * @Description TODO
 * @Date 2024/11/19 18:44
 * @Created by 陈义
 */
@RestController
public class PageItemController {

    @Autowired
    private SimpleJobLauncher jobLauncher;

    @Autowired
    @Qualifier("pageItemJob")
    private Job pageItemJob;

    @Autowired
    @Qualifier("jpaPageItemJob")
    private Job jpaPageItemJob;

    @Autowired
    @Qualifier("demoPageItemJob")
    private Job demoPageItemJob;

    //使用JDBCPageItemReader
    @GetMapping("/page-item-demo")
    public void helloWorld(String name) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("executeTime", LocalDateTime.now().toString())
                .toJobParameters();
        jobLauncher.run(pageItemJob, jobParameters);
    }

    //使用JDBCPageItemReader
    @GetMapping("/jpa-page-item-demo")
    public void jpaPageItemDemo(String name) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("executeTime", LocalDateTime.now().toString())
                .toJobParameters();
        jobLauncher.run(jpaPageItemJob, jobParameters);
    }

    //自定义PageItemReader
    @GetMapping("/abstract-page-item-demo")
    public void abstractPageItemDemo(String name) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("executeTime", LocalDateTime.now().toString())
                .toJobParameters();
        jobLauncher.run(demoPageItemJob, jobParameters);
    }
}
