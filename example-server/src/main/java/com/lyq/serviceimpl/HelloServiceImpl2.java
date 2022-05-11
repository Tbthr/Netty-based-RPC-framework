package com.lyq.serviceimpl;

import com.lyq.Hello;
import com.lyq.HelloService;
import com.lyq.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RpcService(group = "test2", version = "version2")
public class HelloServiceImpl2 implements HelloService {

    static {
        System.out.println("HelloServiceImpl2被创建");
    }

    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl收到: {}.", hello.toString());
        return hello.toString();
    }
}
