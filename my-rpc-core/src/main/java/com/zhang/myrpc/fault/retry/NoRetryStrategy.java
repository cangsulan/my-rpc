package com.zhang.myrpc.fault.retry;

import com.zhang.myrpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * 不重试 - 重试策略
 */
@Slf4j
public class NoRetryStrategy implements RetryStrategy {

    /**
     * 重试
     */
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        // 直接调用一次 callable.call()。
        // 如果这次调用成功，返回 RpcResponse；
        // 如果失败（抛出异常），则直接将异常抛出。
        return callable.call();
    }

}
