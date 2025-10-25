package com.zhang.example.provider;

import com.zhang.example.common.service.UserService;
import com.zhang.myrpc.RpcApplication;
import com.zhang.myrpc.registry.LocalRegistry;
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
        //RpcApplication.init();

        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
