package com.chenyi.yanhuohui.common;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * @Classname CommonStep
 * @Description TODO
 * @Date 2025/1/14 16:28
 * @Created by 陈义
 */
public class CommonTasklet implements Tasklet {

    private String myParam;  // 通用参数

    // 构造函数，接收参数
    public CommonTasklet(String myParam) {
        this.myParam = myParam;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        // 使用传入的参数执行任务
        System.out.println("Executing Tasklet with parameter: " + myParam);

        // 返回状态，决定是否继续执行
        return RepeatStatus.FINISHED;
    }
}
