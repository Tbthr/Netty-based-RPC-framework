package com.lyq.remoting.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcMessage {

    /**
     * rpc 消息类型（ping/pong/常规请求）
     */
    private byte messageType;
    /**
     * 序列化类型
     */
    private byte codec;
    /**
     * 压缩类型
     */
    private byte compress;
    /**
     * 请求 ID（用 IntegerAtomic 生成）
     */
    private int requestId;
    /**
     * 请求数据（其中包含一个 requestId，是 String 类型
     * 用以 UnprocessedRequests 中 CompletableFuture 来异步处理）
     */
    private Object data;

}
