package com.lyq.loadbalance.loadbalancer;

import com.lyq.loadbalance.AbstractLoadBalance;
import com.lyq.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * 随机负载实现
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    // Random 类不是线程安全的
    private static final ThreadLocal<Random> RANDOM_THREAD_LOCAL = ThreadLocal.withInitial(Random::new);

    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        Random random = RANDOM_THREAD_LOCAL.get();
        return serviceAddresses.get(random.nextInt(serviceAddresses.size()));
    }
}
