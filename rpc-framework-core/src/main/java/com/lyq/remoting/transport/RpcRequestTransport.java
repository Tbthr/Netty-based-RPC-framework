package com.lyq.remoting.transport;

import com.lyq.extension.SPI;
import com.lyq.remoting.dto.RpcRequest;

/**
 * send RpcRequest
 */
@SPI
public interface RpcRequestTransport {
    /**
     * send rpc request to server and get result
     *
     * @param rpcRequest message body
     * @return data from server
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}
