package com.zhang.myrpc.springboot.starter.annotation;

import com.zhang.myrpc.springboot.starter.bootstrap.RpcConsumerBootstrap;
import com.zhang.myrpc.springboot.starter.bootstrap.RpcInitBootstrap;
import com.zhang.myrpc.springboot.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *   启用 Rpc 注解
 *   用于全局标识项目需要引入Rpc框架、执行初始化方法
 *
 * @author 30241*/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {

    /**
     * 需要启动 http server，用来区分 是 服务提供者 还是 服务消费者
     *
     */
    boolean needServer() default true;
}
