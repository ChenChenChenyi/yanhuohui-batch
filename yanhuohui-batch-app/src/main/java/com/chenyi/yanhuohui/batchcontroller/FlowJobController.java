package com.chenyi.yanhuohui.batchcontroller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname FlowJobController
 * @Description TODO
 * @Date 2024/11/18 10:54
 * @Created by 陈义
 */
@RestController
public class FlowJobController {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SimpleJobLauncher jobLauncher;

    @GetMapping("/flow-split-job")
    public void csvFileJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run((Job)applicationContext.getBean("parallelFlowsplitJob"), jobParameters);
    }
}
