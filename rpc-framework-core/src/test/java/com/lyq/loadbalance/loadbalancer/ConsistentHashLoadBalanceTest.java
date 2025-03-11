package com.lyq.loadbalance.loadbalancer;

import com.lyq.DemoRpcService;
import com.lyq.DemoRpcServiceImpl;
import com.lyq.config.RpcServiceConfig;
import com.lyq.extension.ExtensionLoader;
import com.lyq.loadbalance.LoadBalance;
import com.lyq.remoting.dto.RpcRequest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

class ConsistentHashLoadBalanceTest {
    @Test
    void TestConsistentHashLoadBalance() {
        LoadBalance loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("loadBalance");
        List<String> serviceUrlList = new ArrayList<>(Arrays.asList("127.0.0.1:9991",
                "127.0.0.1:9992", "127.0.0.1:9993", "127.0.0.1:9994", "127.0.0.1:9995",
                "127.0.0.1:9996", "127.0.0.1:9997", "127.0.0.1:9998", "127.0.0.1:9999"));

        DemoRpcService demoRpcService = new DemoRpcServiceImpl();
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                .group("test2").version("version2").service(demoRpcService).build();

        RpcRequest rpcRequest = RpcRequest.builder()
                .parameters(new Object[]{"sayhelooloo", "sayhelooloosayhelooloo"})
                .interfaceName(rpcServiceConfig.getServiceName())
                .requestId(UUID.randomUUID().toString())
                .group(rpcServiceConfig.getGroup())
                .version(rpcServiceConfig.getVersion())
                .build();

        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println(loadBalance.selectServiceAddress(serviceUrlList, rpcRequest));
    }
}