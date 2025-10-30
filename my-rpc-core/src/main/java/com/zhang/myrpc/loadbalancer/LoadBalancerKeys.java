package com.zhang.myrpc.loadbalancer;

/**
 * 负载均衡器键名常量
 */
public interface LoadBalancerKeys {

    /**
     * 轮询
     */
    String ROUND_ROBIN = "roundRobin";

    /**
     * 简单随机
     */
    String RANDOM = "random";

    /**
     * 一致性 hash
     */
    String CONSISTENT_HASH = "consistentHash";

}
