package com.lyq.provider;

import com.lyq.config.RpcServiceConfig;

/**
 * 存储并提供服务
 */
public interface ServiceProvider {

    /**
     * @param rpcServiceConfig version + group + service
     */
    void addService(RpcServiceConfig rpcServiceConfig);

    /**
     * 根据服务名称返回服务实体
     */
    Object getService(String rpcServiceName);

    /**
     * @param rpcServiceConfig version + group + service
     */
    void publishService(RpcServiceConfig rpcServiceConfig);

}
