package com.zhang.myrpc.fault.tolerant;

/**
 * @author 30241
 * @version 1.0
 * @description: config配置中的可选常量
 * @date 2025/10/30 下午8:05
 */
public interface TolerantStrategyKeys {

    /**
     * 故障恢复
     */
    String FAIL_BACK = "failBack";

    /**
     * 快速失败
     */
    String FAIL_FAST = "failFast";

    /**
     * 故障转移
     */
    String FAIL_OVER = "failOver";

    /**
     * 静默处理
     */
    String FAIL_SAFE = "failSafe";

}
