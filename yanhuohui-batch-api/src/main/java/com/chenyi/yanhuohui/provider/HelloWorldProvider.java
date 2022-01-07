package com.chenyi.yanhuohui.provider;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "yanhuohui-batch", contextId = "HelloWorldProvider")
public interface HelloWorldProvider {

    @PostMapping("/hello")
    void helloWorld(@RequestParam("name") String name);
}
