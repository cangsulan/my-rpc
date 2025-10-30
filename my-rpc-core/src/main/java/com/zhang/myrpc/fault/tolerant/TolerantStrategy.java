package com.zhang.myrpc.fault.tolerant;

import com.zhang.myrpc.model.RpcResponse;

import java.io.IOException;
import java.util.Map;

/**
 * @author 30241
 * @version 1.0
 * @description: 容错策略
 * @date 2025/10/30 下午7:58
 */
public interface TolerantStrategy {

    /**
     * 容错
     * @param context 上下文，用于传递数据
     * @param e       异常
     * */
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) throws IOException;
}
