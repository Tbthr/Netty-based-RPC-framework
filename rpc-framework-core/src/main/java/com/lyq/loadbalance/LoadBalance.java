package com.lyq.loadbalance;

import com.lyq.extension.SPI;
import com.lyq.remoting.dto.RpcRequest;

import java.util.List;

@SPI
public interface LoadBalance {
    /**
     * 从服务地址列表中选择一个返回
     *
     * @param serviceUrlList 服务地址列表
     * @param rpcRequest 服务请求实体
     * @return 被选中的某个地址
     */
    String selectServiceAddress(List<String> serviceUrlList, RpcRequest rpcRequest);
}
