package com.zhang.myrpc.server;

/**
 * @author 30241
 * @version 1.0
 * @description: HTTP 服务器接口
 * @date 2025/10/22 下午5:22
 */
public interface HttpServer {

    // 启动服务器
    void doStart(int port);
}
