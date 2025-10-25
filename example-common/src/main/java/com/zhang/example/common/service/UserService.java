package com.zhang.example.common.service;


import com.zhang.example.common.model.User;

public interface UserService {

    /**
     * @description:  获取用户
     * @param: User
     * @return:  User
     * @author 30241
     * @date: 2025/10/22 下午4:40
     */
    User getUser(User user);

    /**
     * 新方法 - 获取数字
     */
    default short getNumber() {
        return 1;
    }
}
