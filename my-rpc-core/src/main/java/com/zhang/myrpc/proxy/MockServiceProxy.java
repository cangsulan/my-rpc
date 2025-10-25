package com.zhang.myrpc.proxy;

import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Random;

/**
 * @author 30241
 * @version 1.0
 * @description: MOCK服务代理（JDK 动态代理 方式））
 * @date 2025/10/25 下午5:09
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {

    // 使用第三方伪造数据生成库 EasyRandom
    private static final EasyRandomParameters parameters = new EasyRandomParameters()
            .seed(System.currentTimeMillis())
            .stringLengthRange(4, 8)
            .collectionSizeRange(1, 3)
            .dateRange(LocalDate.of(2000, 1, 1), LocalDate.now())
            .randomize(Integer.class, () -> new Random().nextInt(100));

    private static final EasyRandom generator = new EasyRandom(parameters);

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 根据方法的返回值类型，生成特定的默认值对象
        Class<?> returnType = method.getReturnType();
        log.info("mock invoke {}", method.getName());
        return getDefaultObject(returnType);
    }
    /**
     * 生成指定类型的默认值对象（可自行完善默认值逻辑）
     *
     */
    private Object getDefaultObject(Class<?> type) {
        return generator.nextObject(type);
    }
}
