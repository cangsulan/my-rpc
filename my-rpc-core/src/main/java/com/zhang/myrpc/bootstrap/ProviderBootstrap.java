package com.zhang.myrpc.bootstrap;

import com.zhang.myrpc.RpcApplication;
import com.zhang.myrpc.config.RegistryConfig;
import com.zhang.myrpc.config.RpcConfig;
import com.zhang.myrpc.model.ServiceMetaInfo;
import com.zhang.myrpc.model.ServiceRegisterInfo;
import com.zhang.myrpc.registry.LocalRegistry;
import com.zhang.myrpc.registry.Registry;
import com.zhang.myrpc.registry.RegistryFactory;
import com.zhang.myrpc.server.HttpServer;
import com.zhang.myrpc.server.VertxHttpServer;

import java.util.List;

/**
 * @author 30241
 * @version 1.0
 * @description: 服务提供者启动类（初始化）
 * @date 2025/10/31 上午9:24
 */
public class ProviderBootstrap {

    // 初始化
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList){
        // RPC 框架初始化（读取配置 ，注册中心初始化）
        RpcApplication.init();

        // 获取配置
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 获取注册中心
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());

        // 注册服务到本地 和 注册中心
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 注册到本地
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());
            // 注册到 注册中心
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + "服务注册失败：" + e);
            }
        }

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
