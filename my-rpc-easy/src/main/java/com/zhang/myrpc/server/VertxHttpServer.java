package com.zhang.myrpc.server;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * @author 30241
 * @version 1.0
 * @description: TODO
 * @date 2025/10/22 下午5:24
 */
public class VertxHttpServer implements HttpServer{
    @Override
    public void doStart(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();
        // 创建 HTTP 服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 监听端口并处理请求
        server.requestHandler(new HttpServerHandler());

        // 启动 HTTP 服务器 并 监听指定端口
        server.listen(port, result ->{
            if(result.succeeded()){
                System.out.println("Server is now listening on port " + port);
            }else{
                System.out.println("Failed to start server: " + result);
            }
        });
    }

}
