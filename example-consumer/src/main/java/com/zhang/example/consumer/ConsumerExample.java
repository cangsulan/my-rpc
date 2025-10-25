package com.zhang.example.consumer;


import com.zhang.example.common.model.User;
import com.zhang.example.common.service.UserService;
import com.zhang.myrpc.config.RpcConfig;
import com.zhang.myrpc.proxy.ServiceProxyFactory;
import com.zhang.myrpc.utils.ConfigUtils;

/**
 * @author 30241
 * @version 1.0
 * @description: TODO
 * @date 2025/10/25 下午2:53
 */
public class ConsumerExample {

    public static void main(String[] args) {
//        //测试配置
//        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
//        System.out.println(rpc);



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
        long number = userService.getNumber();
        System.out.println(number);
    }
}
