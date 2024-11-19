package com.chenyi.yanhuohui.provider;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value = "yanhuohui-batch", contextId = "HelloWorldProvider")
public interface HelloWorldProvider {

    @GetMapping("/hello")
    void helloWorld(@RequestParam("name") String name);
}
