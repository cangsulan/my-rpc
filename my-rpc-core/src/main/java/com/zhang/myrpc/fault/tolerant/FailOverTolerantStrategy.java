package com.zhang.myrpc.fault.tolerant;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.zhang.myrpc.RpcApplication;
import com.zhang.myrpc.config.RpcConfig;
import com.zhang.myrpc.fault.retry.RetryStrategy;
import com.zhang.myrpc.fault.retry.RetryStrategyFactory;
import com.zhang.myrpc.fault.retry.RetryStrategyKeys;
import com.zhang.myrpc.loadbalancer.LoadBalancer;
import com.zhang.myrpc.loadbalancer.LoadBalancerFactory;
import com.zhang.myrpc.model.RpcRequest;
import com.zhang.myrpc.model.RpcResponse;
import com.zhang.myrpc.model.ServiceMetaInfo;
import com.zhang.myrpc.serializer.Serializer;
import com.zhang.myrpc.serializer.SerializerFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 转移到其他服务节点 - 容错策略
 * @author 30241
 */
@Slf4j
public class FailOverTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) throws IOException {
        //  获取其他服务节点并调用，一直尝试到成功为止


        List<ServiceMetaInfo> serviceMetaInfoList = (List<ServiceMetaInfo>) context.get("serviceMetaInfoList");
        ServiceMetaInfo failedServiceMetaInfo = (ServiceMetaInfo) context.get("failedServiceMetaInfo");
        RpcRequest rpcRequest = (RpcRequest) context.get("rpcRequest");

        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 序列化
        byte[] bodyBytes = serializer.serialize(rpcRequest);

        // 去掉上次失败的服务节点
        serviceMetaInfoList.remove(failedServiceMetaInfo);

        // 重新选一个新的节点进行尝试
        // 负载均衡
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("methodName", rpcRequest.getMethodName());

        RpcResponse rpcResponse = null;
        while (serviceMetaInfoList.size() > 0 || rpcResponse != null) {
            ServiceMetaInfo currentServiceMetaInfo = loadBalancer.select(requestParamMap, serviceMetaInfoList);
            System.out.println("获取节点：" + currentServiceMetaInfo);
            try {
                //发送tcp请求
                RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
                rpcResponse = retryStrategy.doRetry(() -> {
                    try (HttpResponse httpResponse = HttpRequest.post(currentServiceMetaInfo.getServiceAddress())
                            .body(bodyBytes)
                            .execute()) {
                        byte[] result = httpResponse.bodyBytes();
                        // 反序列化
                        RpcResponse response = serializer.deserialize(result, RpcResponse.class);
                        return response;
                    }
                });
                return rpcResponse;
            } catch (Exception exception) {
                //移除失败节点
                serviceMetaInfoList.remove(currentServiceMetaInfo);
                continue;
            }
        }
        //调用失败
        throw new RuntimeException(e);
    }
}
