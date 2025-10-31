package com.zhang.example.springboot.consumer;

import com.zhang.example.common.model.User;
import com.zhang.example.common.service.UserService;
import com.zhang.myrpc.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl {

    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("zhangsan233");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }

}
