package com.lyq.serviceimpl;

import com.lyq.Hello;
import com.lyq.HelloService;
import com.lyq.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;

/**
 * 通过 @RpcService 自动注册服务
 */
@Slf4j
@RpcService(group = "test1", version = "version1")
public class HelloServiceImpl implements HelloService {

    static {
        System.out.println("HelloServiceImpl被创建");
    }

    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        log.info("HelloServiceImpl返回: {}.", result);
        return result;
    }
}
