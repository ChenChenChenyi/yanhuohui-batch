package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.DefaultJobParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@Slf4j
public class BatchJobDemoController {
    public JobParameters jobParameters;
    @Autowired
    private SimpleJobLauncher jobLauncher;
    @Autowired
    private Job importCsvJob;
    @Autowired
    private DefaultJobParameters defaultJobParameters;

    @RequestMapping("/batch-demo")
    public String imp() throws Exception {
        String fileName = "manager";
        String path = fileName + ".csv";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(System.currentTimeMillis());
        String cut = "2021-12-30";
        jobParameters = new JobParametersBuilder()
                .addString("time", date)
                .addString("path", path)
                .addString("cut",cut)
                .toJobParameters();
        log.info("=====================================batch-demo批处理开启！");
        jobLauncher.run(importCsvJob, jobParameters);
        //默认是同步的，只有当job执行完了才会执行下面的
        //自定义SimpleJobLauncher加入异步的配置才能异步执行
        log.info("=====================================batch-demo批处理完成！");
        return "ok";
    }



}
