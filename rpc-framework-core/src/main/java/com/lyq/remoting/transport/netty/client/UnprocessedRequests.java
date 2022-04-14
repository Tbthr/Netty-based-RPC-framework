package com.lyq.remoting.transport.netty.client;

import com.lyq.remoting.dto.RpcResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> 客户端远程调用时，NettyRpcClient 直接返回 CompletableFuture </p>
 * <p> 待服务端返回结果后，NettyRpcClient 自定义处理器 complete() 存入结果 </p>
 * <p> 代理对象利用 get() 获取结果，这样阻塞的控制权交给了调用 rpc方法的线程，而不是发送请求的 eventLoop 里的线程 </p>
 * <p>（一个 eventLoop 对应一个或多个 Channel，AttributeMap 的结果绑定在 Channel 上，需要阻塞等待）</p>
 */
public class UnprocessedRequests {
    private static final Map<String, CompletableFuture<RpcResponse<Object>>> UNPROCESSED_RESPONSE_FUTURES = new ConcurrentHashMap<>();

    public void put(String requestId, CompletableFuture<RpcResponse<Object>> future) {
        UNPROCESSED_RESPONSE_FUTURES.put(requestId, future);
    }

    public void complete(RpcResponse<Object> rpcResponse) {
        CompletableFuture<RpcResponse<Object>> future = UNPROCESSED_RESPONSE_FUTURES.remove(rpcResponse.getRequestId());
        if (null != future) {
            future.complete(rpcResponse);
        } else {
            throw new IllegalStateException();
        }
    }
}
