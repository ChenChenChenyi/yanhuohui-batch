package com.chenyi.yanhuohui.parallelflow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @Classname ParalellStepExecutionListener
 * @Description TODO
 * @Date 2024/11/18 12:15
 * @Created by 陈义
 */
@Slf4j
public class ParalellStepExecutionListener implements JobExecutionListener {
    private long startTime;
    private long endTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = System.currentTimeMillis();
        log.info("并行step任务处理开始");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        endTime = System.currentTimeMillis();
        log.info("并行step任务处理结束，总耗时=" + (endTime - startTime) + "ms");
    }
}
