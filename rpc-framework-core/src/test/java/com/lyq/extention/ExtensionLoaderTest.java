package com.lyq.extention;

import com.lyq.compress.Compress;
import com.lyq.extension.ExtensionLoader;
import com.lyq.loadbalance.LoadBalance;
import com.lyq.registry.ServiceDiscovery;
import com.lyq.registry.ServiceRegistry;
import com.lyq.remoting.transport.RpcRequestTransport;
import com.lyq.serialize.Serializer;
import org.junit.jupiter.api.Test;

public class ExtensionLoaderTest {
    @Test
    void test() {
        ServiceRegistry serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension("zk");
        ServiceDiscovery serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension("zk");

        LoadBalance loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("loadBalance");

        Compress compress = ExtensionLoader.getExtensionLoader(Compress.class).getExtension("gzip");

        Serializer serializer1 = ExtensionLoader.getExtensionLoader(Serializer.class).getExtension("kyro");
        Serializer serializer2 = ExtensionLoader.getExtensionLoader(Serializer.class).getExtension("hessian");
        Serializer serializer3 = ExtensionLoader.getExtensionLoader(Serializer.class).getExtension("protostuff");

        RpcRequestTransport rpcClient1 = ExtensionLoader.getExtensionLoader(RpcRequestTransport.class).getExtension("netty");
        RpcRequestTransport rpcClient2 = ExtensionLoader.getExtensionLoader(RpcRequestTransport.class).getExtension("socket");
    }
}
