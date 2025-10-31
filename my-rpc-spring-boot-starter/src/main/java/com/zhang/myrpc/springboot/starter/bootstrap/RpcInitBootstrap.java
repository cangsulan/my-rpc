package com.zhang.myrpc.springboot.starter.bootstrap;

import com.zhang.myrpc.RpcApplication;
import com.zhang.myrpc.config.RpcConfig;
import com.zhang.myrpc.server.HttpServer;
import com.zhang.myrpc.server.VertxHttpServer;
import com.zhang.myrpc.springboot.starter.annotation.EnableRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author 30241
 * @version 1.0
 * @description: Rpc框架全局启动类
 *              在 Spring 框架初始化时，获取 @EnableRpc 注解的属性，并初始化 RPC 框架
 * @date 2025/10/31 上午10:14
 */
@Slf4j
public class RpcInitBootstrap implements ImportBeanDefinitionRegistrar {

    /**
     * Spring 初始化时执行，初始化 RPC 框架
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        // 获取 EnableRpc 注解的属性值
        boolean needServer = (boolean) importingClassMetadata.getAnnotationAttributes(EnableRpc.class.getName())
                .get("needServer");

        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();

        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 启动服务器
        if (needServer) {
            // 启动 web 服务
            HttpServer httpServer = new VertxHttpServer();
            httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
        } else {
            log.info("不启动 server");
        }

    }
}
