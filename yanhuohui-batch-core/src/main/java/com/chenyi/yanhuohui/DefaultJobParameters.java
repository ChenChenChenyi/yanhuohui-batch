package com.chenyi.yanhuohui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.stereotype.Component;

import java.beans.SimpleBeanInfo;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Component
public class DefaultJobParameters implements JobParametersIncrementer {
    private final DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
    @Override
    public JobParameters getNext(JobParameters jobParameters) {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        String startTime = LocalDateTime.now().format(sf);

        Long retryTime;
        if(Objects.isNull(jobParameters)){
            retryTime = 0L;
        }else {
            retryTime = jobParameters.getLong("retryTime",Long.valueOf(0L));
        }

        //相同的作业参数执行的job是同一个，springBatch不允许其重复执行
        //通过设置不同的参数，使批量能重复发起
        jobParametersBuilder.addString("startTime",startTime);
        jobParametersBuilder.addLong("retryTime",retryTime);

        return jobParametersBuilder.toJobParameters();
    }
}
