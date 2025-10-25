package com.zhang.myrpc.proxy;

import java.lang.reflect.Proxy;

/**
 * @author 30241
 * @version 1.0
 * @description: 服务代理工厂，来简化代理对象的创建过程
 * @date 2025/10/22 下午11:07
 */
public class ServiceProxyFactory {

    // 根据 服务类 来创建代理对象
    public static <T> T getProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }
}
