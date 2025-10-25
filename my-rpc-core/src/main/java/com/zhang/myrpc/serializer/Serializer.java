package com.zhang.myrpc.serializer;

import java.io.IOException;

/**
 * @author 30241
 * @version 1.0
 * @description: 序列化器接口
 * @date 2025/10/22 下午6:00
 */
public interface Serializer {
    // 序列化
    <T> byte[] serialize(T object) throws IOException;

    // 反序列化
    <T> T deserialize(byte[] bytes, Class<T> type) throws IOException;
}
