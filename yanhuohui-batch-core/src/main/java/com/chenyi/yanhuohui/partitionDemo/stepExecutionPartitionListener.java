package com.chenyi.yanhuohui.partitionDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class stepExecutionPartitionListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Thread name -> ["+Thread.currentThread().getName()+"] -- stepName ->["+
                stepExecution.getStepName()+"] -- ExecutionContext -> [" +
                stepExecution.getExecutionContext() +"] 开始！");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Thread name -> ["+Thread.currentThread().getName()+"] -- stepName ->["+
                stepExecution.getStepName()+"] -- ExecutionContext -> [" +
                stepExecution.getExecutionContext() +"] 状态为："+stepExecution.getExitStatus()+" 结束！");
        return null;
    }
}
