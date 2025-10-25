package com.zhang.example.consumer;


import com.zhang.myrpc.config.RpcConfig;
import com.zhang.myrpc.utils.ConfigUtils;

/**
 * @author 30241
 * @version 1.0
 * @description: TODO
 * @date 2025/10/25 下午2:53
 */
public class ConsumerExample {

    public static void main(String[] args) {
        //加载配置
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
    }
}
