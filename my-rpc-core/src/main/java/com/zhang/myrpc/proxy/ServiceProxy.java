package com.zhang.myrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.zhang.myrpc.RpcApplication;
import com.zhang.myrpc.config.RpcConfig;
import com.zhang.myrpc.constant.RpcConstant;
import com.zhang.myrpc.fault.retry.RetryStrategy;
import com.zhang.myrpc.fault.retry.RetryStrategyFactory;
import com.zhang.myrpc.fault.tolerant.TolerantStrategy;
import com.zhang.myrpc.fault.tolerant.TolerantStrategyFactory;
import com.zhang.myrpc.loadbalancer.LoadBalancer;
import com.zhang.myrpc.loadbalancer.LoadBalancerFactory;
import com.zhang.myrpc.model.RpcRequest;
import com.zhang.myrpc.model.RpcResponse;
import com.zhang.myrpc.model.ServiceMetaInfo;
import com.zhang.myrpc.registry.Registry;
import com.zhang.myrpc.registry.RegistryFactory;
import com.zhang.myrpc.serializer.Serializer;
import com.zhang.myrpc.serializer.SerializerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 30241
 * @version 1.0
 * @description: 服务代理（JDK动态代理）
 * @date 2025/10/22 下午10:54
 */
public class ServiceProxy implements InvocationHandler {

    // 调用代理
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();

        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 发送请求

            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);

            // serviceMetaInfo.setServiceVersion();

            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }

            // 调用负载均衡算法，选中目标地址
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            // 将调用方法名（请求路径）作为负载均衡参数
            Map<String,Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

            RpcResponse rpcResponse;
            try {
                // 使用重试机制
                RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
                // 发送请求
                rpcResponse = retryStrategy.doRetry(() -> {
                    try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
                            .body(bodyBytes)
                            .execute()) {
                        byte[] result = httpResponse.bodyBytes();
                        // 反序列化
                        RpcResponse response = serializer.deserialize(result, RpcResponse.class);
                        return response;
                    }
                });
            }catch (Exception e){
                // 容错机制
                TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
                Map<String,Object> params = new HashMap<>();
                params.put("rpcRequest", rpcRequest);
                params.put("failedServiceMetaInfo",selectedServiceMetaInfo);
                params.put("serviceMetaInfoList", serviceMetaInfoList);
                rpcResponse = tolerantStrategy.doTolerant(params, e);
            }
            return rpcResponse.getData();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
