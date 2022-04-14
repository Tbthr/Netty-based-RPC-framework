package com.lyq.registry;

import com.lyq.remoting.dto.RpcRequest;
import com.lyq.extension.SPI;

import java.net.InetSocketAddress;

@SPI
public interface ServiceDiscovery {
    /**
     * 通过 rpcServiceName 查找服务
     *
     * @param rpcRequest rpc 请求实体类
     * @return 服务地址
     */
    InetSocketAddress lookupService(RpcRequest rpcRequest);
}
