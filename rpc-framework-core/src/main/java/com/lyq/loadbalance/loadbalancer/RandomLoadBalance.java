package com.lyq.loadbalance.loadbalancer;

import com.lyq.loadbalance.AbstractLoadBalance;
import com.lyq.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * 随机负载实现
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceAddresses.get(random.nextInt(serviceAddresses.size()));
    }
}
