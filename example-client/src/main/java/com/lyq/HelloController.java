package com.lyq;

import com.lyq.annotation.RpcReference;
import org.springframework.stereotype.Component;

@Component
public class HelloController {

    @RpcReference(version = "version1", group = "test1")
    private HelloService helloService;

    public void test() {
        Hello hello = new Hello("111", "222");
        for (int i = 0; i < 10; i++) {
            String s = helloService.hello(hello);
            System.out.println("s = " + s);
            // 如需使用 assert 断言，需要在 VM options 添加参数：-ea
            assert "Hello description is 222".equals(s);
        }
    }

    public void testForConcurrent() throws InterruptedException {
        Thread.sleep(30_000);
        // test for concurrent
        long run1 = TestForConcurrent.run(helloService, 10, 100);
        System.out.println("第1轮测试：run1 = " + run1);
        Thread.sleep(5000);
        long run2 = TestForConcurrent.run(helloService, 10, 200);
        System.out.println("第2轮测试：run2 = " + run2);
        Thread.sleep(5000);
        long run3 = TestForConcurrent.run(helloService, 10, 300);
        System.out.println("第3轮测试：run3 = " + run3);
        Thread.sleep(5000);
        long run4 = TestForConcurrent.run(helloService, 10, 400);
        System.out.println("第4轮测试：run4 = " + run4);
        Thread.sleep(5000);
        long run5 = TestForConcurrent.run(helloService, 10, 500);
        System.out.println("第5轮测试：run5 = " + run5);
        Thread.sleep(5000);
        long run6 = TestForConcurrent.run(helloService, 10, 600);
        System.out.println("第6轮测试：run6 = " + run6);
        Thread.sleep(5000);
        long run7 = TestForConcurrent.run(helloService, 10, 700);
        System.out.println("第7轮测试：run7 = " + run7);
        Thread.sleep(5000);
        long run8 = TestForConcurrent.run(helloService, 10, 800);
        System.out.println("第8轮测试：run8 = " + run8);
        Thread.sleep(5000);
        long run9 = TestForConcurrent.run(helloService, 10, 900);
        System.out.println("第9轮测试：run9 = " + run9);
        Thread.sleep(5000);
        long run10 = TestForConcurrent.run(helloService, 10, 1000);
        System.out.println("第10轮测试：run10 = " + run10);
        Thread.sleep(5000);
    }
}
