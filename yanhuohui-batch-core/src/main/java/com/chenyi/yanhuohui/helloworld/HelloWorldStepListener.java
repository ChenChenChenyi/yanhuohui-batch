package com.chenyi.yanhuohui.helloworld;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import javax.batch.api.listener.StepListener;

/**
 * @Classname HelloWorldStepListener
 * @Description TODO
 * @Date 2024/11/14 9:16
 * @Created by 陈义
 */
@Component
public class HelloWorldStepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        // Step 开始前的初始化或日志记录
        System.out.println("HelloWorldStep " + stepExecution.getStepName() + " is starting...");
        stepExecution.getExecutionContext().put("executeParam1","20250109");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        // 统计信息汇总
        int readCount = stepExecution.getReadCount();
        int writeCount = stepExecution.getWriteCount();
        int skipCount = stepExecution.getSkipCount();

        System.out.println("Step " + stepExecution.getStepName() + " finished.");
        System.out.println("Read Count: " + readCount);
        System.out.println("Write Count: " + writeCount);
        System.out.println("Skip Count: " + skipCount);

        System.out.println("HelloWorldStep " + stepExecution.getStepName() + " end.");
        // 其他详细信息可以从 stepExecution 中获取
        return stepExecution.getExitStatus();
    }
}
