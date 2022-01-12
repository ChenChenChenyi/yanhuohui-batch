package com.chenyi.yanhuohui.partitionDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.StepExecutionAggregator;

import java.util.Collection;

@Slf4j
public class MyStepExecutionAggregator implements StepExecutionAggregator {
    /**
     * 聚合
     *
     * @param masterExecution masterStep 结果
     * @param slaveExecutions slaveStep 结果集合
     */
    @Override
    public void aggregate(StepExecution masterExecution, Collection<StepExecution> slaveExecutions) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("masterStep = {}", masterExecution.toString());
        slaveExecutions.forEach(slaveExecution -> log.info(slaveExecution.toString()));
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
