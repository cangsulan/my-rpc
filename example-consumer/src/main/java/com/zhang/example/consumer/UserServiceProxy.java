package com.zhang.example.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.zhang.example.common.model.User;
import com.zhang.example.common.service.UserService;
import com.zhang.myrpc.model.RpcRequest;
import com.zhang.myrpc.model.RpcResponse;
import com.zhang.myrpc.serializer.JdkSerializer;
import com.zhang.myrpc.serializer.Serializer;

import java.io.IOException;

/**
 * @author 30241
 * @version 1.0
 * @description: TODO
 * @date 2025/10/22 下午10:13
 */
public class UserServiceProxy implements UserService {

    @Override
    public User getUser(User user) {

        // 指定 序列化器
        Serializer serializer = new JdkSerializer();

        // 发请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();

        try{
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;
            try(HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()){
                result = httpResponse.bodyBytes();
            }
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
