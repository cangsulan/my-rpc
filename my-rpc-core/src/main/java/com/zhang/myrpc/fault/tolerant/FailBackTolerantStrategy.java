package com.zhang.myrpc.fault.tolerant;

import com.zhang.myrpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 降级到其他服务 - 容错策略
 */
@Slf4j
public class FailBackTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // todo 获取降级的服务并调用
        // todo 可以考虑使用 接口Mock，得到一个假数据，当然更好的是让服务自己写一个降级方法或降级类，然后直接调用即可
        return null;
    }
}
