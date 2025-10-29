package com.zhang.example.provider;

import com.zhang.example.common.service.UserService;
import com.zhang.myrpc.RpcApplication;
import com.zhang.myrpc.config.RegistryConfig;
import com.zhang.myrpc.config.RpcConfig;
import com.zhang.myrpc.model.ServiceMetaInfo;
import com.zhang.myrpc.registry.LocalRegistry;
import com.zhang.myrpc.registry.Registry;
import com.zhang.myrpc.registry.RegistryFactory;
import com.zhang.myrpc.server.HttpServer;
import com.zhang.myrpc.server.VertxHttpServer;

/**
 * @author 30241
 * @version 1.0
 * @description: TODO
 * @date 2025/10/25 下午2:57
 */
public class ProviderExample {

    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
