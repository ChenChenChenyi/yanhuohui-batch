package com.chenyi.yanhuohui.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname MySampleXxlJob
 * @Description 一些自己写的简单的测试批量任务
 * @Date 2025/3/6 10:54
 * @Created by 陈义
 */
@Component
public class MySampleXxlJob {

    private static Logger logger = LoggerFactory.getLogger(SampleXxlJob.class);

    @XxlJob("myDemoJobHandler")
    public void demoJobHandler() throws Exception {
        XxlJobHelper.log("=========================myDemoJob任务开始=======================");
        logger.info("=========================myDemoJob任务开始=======================");
        logger.info("Hello chenyi!");
        Thread.sleep(10000);
        logger.info("=========================myDemoJob任务结束=======================");
        XxlJobHelper.log("=========================myDemoJob任务结束=======================");
    }
}
