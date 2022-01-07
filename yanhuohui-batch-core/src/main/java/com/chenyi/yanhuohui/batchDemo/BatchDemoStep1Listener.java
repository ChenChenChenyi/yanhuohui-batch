package com.chenyi.yanhuohui.batchDemo;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class BatchDemoStep1Listener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("step1开始。");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("step1结束,状态为" + stepExecution.getExitStatus().getExitCode());
        return stepExecution.getExitStatus();
    }
}
