package com.zhang.example.consumer;


import com.zhang.example.common.model.User;
import com.zhang.example.common.service.UserService;
import com.zhang.myrpc.bootstrap.ConsumerBootstrap;
import com.zhang.myrpc.config.RpcConfig;
import com.zhang.myrpc.proxy.ServiceProxyFactory;
import com.zhang.myrpc.utils.ConfigUtils;

import java.beans.Customizer;

/**
 * @author 30241
 * @version 1.0
 * @description: TODO
 * @date 2025/10/25 下午2:53
 */
public class ConsumerExample {

    public static void main(String[] args) throws InterruptedException {
//        //测试配置
//        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
//        System.out.println(rpc);

        // 初始化
        ConsumerBootstrap.init();

        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("zhangsan");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
        // 多次调用

        User newUser2 = userService.getUser(user);
        if (newUser2 != null) {
            System.out.println(newUser2.getName());
        } else {
            System.out.println("user == null");
        }

        Thread.sleep(2*1000);

        User newUser3 = userService.getUser(user);
        if (newUser3 != null) {
            System.out.println(newUser3.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
