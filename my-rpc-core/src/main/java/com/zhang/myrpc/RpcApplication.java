package com.zhang.myrpc;

import cn.hutool.core.util.StrUtil;
import com.zhang.myrpc.config.RegistryConfig;
import com.zhang.myrpc.config.RpcConfig;
import com.zhang.myrpc.constant.RpcConstant;
import com.zhang.myrpc.registry.Registry;
import com.zhang.myrpc.registry.RegistryFactory;
import com.zhang.myrpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 30241
 * @version 1.0
 * @description: RPC 框架应用，
 *               相当于 holder，存放了项目全局用到的变量。双检锁单例模式实现
 * @date 2025/10/25 下午2:13
 */
@Slf4j
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;

    // 框架初始化，支持传入自定义配置
    public static void init(RpcConfig newRpcConfig){
        rpcConfig = newRpcConfig;
        log.info("rpc init, config = {}", newRpcConfig.toString());

        // 注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init, config = {}", registryConfig);
    }

    // 框架初始化，从配置文件读取
    public static void init(){
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            // 配置加载失败，使用默认值
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    // 获取配置
    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }

}
