package com.chenyi.yanhuohui.batchcontroller;

import com.chenyi.yanhuohui.common.utils.RedisKeyUtil;
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
 * @Classname CsvFileImportController
 * @Description TODO
 * @Date 2024/11/12 18:53
 * @Created by 陈义
 */
@RestController
public class CsvFileImportController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SimpleJobLauncher jobLauncher;

    //key = yanhuohui_goods:test:batchDate
    private static final String REDISKEY_TEST = RedisKeyUtil.goodsKeyBuilder("test","batchDate");

    @GetMapping("/csv-file-job")
    public void csvFileJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addString("filename", "D:\\烟火会\\project\\yanhuohui-batch\\yanhuohui-batch-app\\src\\main\\resources\\manager.csv")
                .addString("batchDate","20241118")
                .toJobParameters();
        jobLauncher.run((Job)applicationContext.getBean("csvFileimportJob"), jobParameters);
    }
}
