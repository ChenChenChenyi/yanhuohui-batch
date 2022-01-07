package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import com.chenyi.yanhuohui.provider.HelloProvider;
import com.chenyi.yanhuohui.provider.HelloWorldProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController implements HelloWorldProvider {

    @Autowired
    private HelloProvider helloProvider;

    @Override
    public void helloWorld(String name) {
        System.out.println("Hello batch " + name + "!");
        BaseResponse response = helloProvider.helloWorld("zhuzhu");
        //throw new SbcRuntimeException(CommonErrorCode.FAILED,"我自己抛出的错误");
        //return BaseResponse.SUCCESSFUL();
    }
}
