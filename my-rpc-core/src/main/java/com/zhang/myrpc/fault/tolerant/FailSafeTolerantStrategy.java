package com.zhang.myrpc.fault.tolerant;

import com.zhang.myrpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 静默处理异常 - 容错策略
 *
 * @author 30241
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常", e);
        // 记录了异常信息后，直接返回一个 空的 默认数据
        return new RpcResponse();
    }
}