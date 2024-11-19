package com.chenyi.yanhuohui.helloworld;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * @Classname HelloWorldTasklet
 * @Description TODO
 * @Date 2024/11/14 9:04
 * @Created by 陈义
 */
@Component
public class HelloWorldTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        // 假设执行某些读取和写入操作
        int readCount = 100;
        int writeCount = 80;

        System.out.println("HelloWOrldTasklet start..");

        // ... your code

        System.out.println("HelloWorldTasklet done..");

        // 设置统计信息
        //stepContribution.incrementReadCount(readCount);//没有这个方法，可能在高版本里面有
        stepContribution.incrementWriteCount(writeCount);

        // 返回状态，决定是否继续执行
        return RepeatStatus.FINISHED;
    }
}
