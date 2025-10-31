package com.zhang.myrpc.bootstrap;

import com.zhang.myrpc.RpcApplication;

/**
 * @author 30241
 * @version 1.0
 * @description:  服务消费者启动类（初始化）
 * @date 2025/10/31 上午9:39
 */
public class ConsumerBootstrap {

    public static void init(){
        // RPC 框架初始化（读取配置 ，注册中心初始化）
        RpcApplication.init();
    }

}
