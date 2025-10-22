package com.zhang.myrpc.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 30241
 * @version 1.0
 * @description: 本地注册中心
 * @date 2025/10/22 下午5:46
 */
public class LocalRegistry {

    // 注册信息存储
    private static final Map<String, Class<?>> map = new ConcurrentHashMap<String, Class<?>>();

    // 注册服务
    public static void register(String serviceName, Class<?> implClass) {
        map.put(serviceName, implClass);
    }

    // 获取服务
    public static Class<?> get(String serviceName) {
        return map.get(serviceName);
    }

    // 删除服务
    public static void remove(String serviceName) {
        map.remove(serviceName);
    }
}
