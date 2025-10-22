package com.zhang.example.provider;

import com.zhang.example.common.model.User;
import com.zhang.example.common.service.UserService;

/**
 * @author 30241
 * @version 1.0
 * @description: TODO
 * @date 2025/10/22 下午4:59
 */
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
