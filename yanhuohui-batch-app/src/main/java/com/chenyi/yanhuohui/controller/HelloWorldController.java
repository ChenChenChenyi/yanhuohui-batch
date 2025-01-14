package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.primary.manager.ManagerRepository;
import com.chenyi.yanhuohui.provider.HelloWorldProvider;
import com.chenyi.yanhuohui.wangdai.user.UserRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Classname HelloWorldController
 * @Description 测试类，一些简单的demo都在里面
 * @Date 2025/01/08 17:02
 * @Created by 陈义
 */
@RestController
public class HelloWorldController {

    @Autowired
    private SimpleJobLauncher jobLauncher;

    @Autowired
    @Qualifier("helloWorldJob")
    private Job helloWorldJob;

    @GetMapping("/hello")
    public void helloWorld(@RequestParam("name") String name) {
        System.out.println("你好 batch " + name + "!");
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ManagerRepository managerRepository;

     /**
      * @author chenyi
      * @date 2025/1/9 18:01
      * @description: 双数据源的测试
      */

    @GetMapping("/test-jpa")
    public void testJpa() {
        System.out.println(managerRepository.getOne(43L));
        System.out.println(userRepository.getOne(1L));
    }


    @GetMapping("/helloWorldJob")
    public void helloWorldJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("executeTime", LocalDateTime.now().toString())
                .toJobParameters();
        jobLauncher.run(helloWorldJob, jobParameters);
    }



}
