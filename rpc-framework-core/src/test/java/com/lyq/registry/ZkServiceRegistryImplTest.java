package com.lyq.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.lyq.DemoRpcService;
import com.lyq.DemoRpcServiceImpl;
import com.lyq.config.RpcServiceConfig;
import com.lyq.registry.zk.ZkServiceDiscoveryImpl;
import com.lyq.registry.zk.ZkServiceRegistryImpl;
import com.lyq.remoting.dto.RpcRequest;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

class ZkServiceRegistryImplTest {
    @Test
    void should_register_service_successful_and_lookup_service_by_service_name() {
        ServiceRegistry zkServiceRegistry = new ZkServiceRegistryImpl();
        InetSocketAddress givenInetSocketAddress = new InetSocketAddress("127.0.0.1", 9333);
        DemoRpcService demoRpcService = new DemoRpcServiceImpl();
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                .group("test2").version("version2").service(demoRpcService).build();
        zkServiceRegistry.registerService(rpcServiceConfig.getRpcServiceName(), givenInetSocketAddress);
        ServiceDiscovery zkServiceDiscovery = new ZkServiceDiscoveryImpl();
        RpcRequest rpcRequest = RpcRequest.builder()
                .parameters(new Object[]{"sayhelooloo", "sayhelooloosayhelooloo"})
                .interfaceName(rpcServiceConfig.getServiceName())
                .group(rpcServiceConfig.getGroup())
                .version(rpcServiceConfig.getVersion())
                .build();
        InetSocketAddress acquiredInetSocketAddress = zkServiceDiscovery.lookupService(rpcRequest);
        assertEquals(givenInetSocketAddress.toString(), acquiredInetSocketAddress.toString());
    }
}
