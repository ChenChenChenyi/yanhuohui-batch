package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.provider.HelloWorldProvider;
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

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RestController
public class HelloWorldController {

    @Autowired
    private SimpleJobLauncher jobLauncher;

    @Autowired
    @Qualifier("helloWorldJob")
    private Job helloWorldJob;

    @GetMapping("/hello")
    public void helloWorld(String name) {
        System.out.println("Hello batch " + name + "!");
    }

    @GetMapping("/helloWorldJob")
    public void helloWorldJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("executeTime", LocalDateTime.now().toString())
                .toJobParameters();
        jobLauncher.run(helloWorldJob, jobParameters);
    }



}
