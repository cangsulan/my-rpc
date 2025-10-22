package com.zhang.example.provider;

import com.zhang.example.common.service.UserService;
import com.zhang.myrpc.registry.LocalRegistry;
import com.zhang.myrpc.server.HttpServer;
import com.zhang.myrpc.server.VertxHttpServer;

/**
 * @author 30241
 * @version 1.0
 * @description: TODO
 * @date 2025/10/22 下午5:01
 */
public class EasyProviderExample {

    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);
        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
