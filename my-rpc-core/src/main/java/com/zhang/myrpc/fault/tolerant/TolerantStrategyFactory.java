package com.zhang.myrpc.fault.tolerant;

import com.zhang.myrpc.fault.retry.NoRetryStrategy;
import com.zhang.myrpc.fault.retry.RetryStrategy;
import com.zhang.myrpc.spi.SpiLoader;

/**
 * @author 30241
 * @version 1.0
 * @description: 策略工厂
 * @date 2025/10/30 下午8:09
 */
public class TolerantStrategyFactory {

    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错
     */
    private static final TolerantStrategy DEFAULT_TOLERANT_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }


}
