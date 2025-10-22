package com.zhang.example.consumer;

import com.zhang.example.common.model.User;
import com.zhang.example.common.service.UserService;
import com.zhang.myrpc.proxy.ServiceProxyFactory;

/**
 * @author 30241
 * @version 1.0
 * @description: 简易服务消费者示例
 * @date 2025/10/22 下午5:05
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        // todo 需要获取 UserService 的实现类对象
        // 获取动态代理对象
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("zhang");
        // 调用
        User newUser = userService.getUser(user);
        if(newUser != null) {
            System.out.println(newUser.getName());
        }else{
            System.out.println("user == null");
        }
    }
}
