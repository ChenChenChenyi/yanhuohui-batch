package com.chenyi.yanhuohui.provider;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//分布式服务需要定义API并加上feign相关注解，BATCH服务目前不需要搞分布式，这个接口没有用到
//@FeignClient(value = "yanhuohui-batch", contextId = "HelloWorldProvider")
public interface HelloWorldProvider {

    @GetMapping("/hello")
    void helloWorld(@RequestParam("name") String name);
}
