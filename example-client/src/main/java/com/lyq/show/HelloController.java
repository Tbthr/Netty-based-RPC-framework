package com.lyq.show;

import com.lyq.Hello;
import com.lyq.HelloService;
import com.lyq.annotation.RpcReference;
import com.lyq.registry.zk.util.CuratorUtils;
import com.lyq.remoting.transport.netty.client.NettyRpcClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/rpc")
public class HelloController {

    private static final Hello HELLO = new Hello();

    @RpcReference(version = "version1", group = "test1")
    private HelloService helloService;

    @GetMapping("/getNodesMsg")
    public Map<String, List<String>> getNodesMsg() {
        return CuratorUtils.getAllNodes();
    }

    @GetMapping("/sendMsg")
    public String sendMsg(@RequestParam("msg") String msg) {
        if (msg == null || msg.length() == 0 || !msg.contains(",")) {
            return "发送信息有误！";
        }
        String[] split = msg.split(",");
        HELLO.setMessage(split[0]);
        HELLO.setDescription(split[1]);
        String result = helloService.hello(HELLO);
        return "成功接收客户端发起的调用：\n"
                + "\n调用节点：\n" + NettyRpcClient.url.replace("/", "")
                + "\n"
                + "\n返回结果：\n" + result;
    }

    public void test() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Hello hello = new Hello(String.valueOf(i), String.valueOf(i));
            String s = helloService.hello(hello);
            // 如需使用 assert 断言，需要在 VM options 添加参数：-ea
//            assert "Hello description is 222".equals(s);
            log.info("客户端远程调用成功，响应结果为：{}", s);

            Thread.sleep(2000);
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
