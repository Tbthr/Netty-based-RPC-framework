package com.lyq.registry.zk;

import com.lyq.enums.RpcErrorMessageEnum;
import com.lyq.exception.RpcException;
import com.lyq.extension.ExtensionLoader;
import com.lyq.loadbalance.LoadBalance;
import com.lyq.registry.ServiceDiscovery;
import com.lyq.registry.zk.util.CuratorUtils;
import com.lyq.remoting.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.util.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

/**
 * 服务路由
 */
@Slf4j
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {
    private final LoadBalance loadBalance;

    public ZkServiceDiscoveryImpl() {
        this.loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("loadBalance");
    }

    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        String rpcServiceName = rpcRequest.getRpcServiceName();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        log.info("The services list:[{}]", Arrays.toString(serviceUrlList.toArray()));
        if (CollectionUtils.isEmpty(serviceUrlList)) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        // load balancing
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcRequest);
        log.info("Successfully found the service address:[{}]", targetServiceUrl);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}
