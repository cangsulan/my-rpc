package com.zhang.myrpc.loadbalancer;

import com.zhang.myrpc.model.ServiceMetaInfo;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性哈希负载均衡器
 *
 */
public class ConsistentHashLoadBalancer implements LoadBalancer {

    // 存放虚拟节点的 哈希环
    private final TreeMap<Integer, ServiceMetaInfo> hashRing = new TreeMap<Integer, ServiceMetaInfo>();

    // 虚拟节点的个数
    private static final int VIRTUAL_NODE_NUM = 100;

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        // 判断集合size大小
        if(serviceMetaInfoList == null || serviceMetaInfoList.isEmpty()){
            return null;
        }
        int size = serviceMetaInfoList.size();
        // 只有一个选择
        if(size == 1){
            return serviceMetaInfoList.get(0);
        }
        // 负载均衡：

        //为每个节点创建虚拟节点
        for (ServiceMetaInfo metaInfo : serviceMetaInfoList) {
            for (int i = 0; i < VIRTUAL_NODE_NUM; i++) {
                hashRing.put(getHash(metaInfo.getServiceAddress()+"#"+i),metaInfo);
            }
        }

        // 当前请求的 hash
        int hash = getHash(requestParams);
        // 选择最接近且大于等于调用请求 hash 值的虚拟节点
        Map.Entry<Integer, ServiceMetaInfo> entry = hashRing.ceilingEntry(hash);
        if(entry == null){
            // 如果没有大于等于调用请求 hash 值的虚拟节点，则返回环首部的节点
            return hashRing.firstEntry().getValue();
        }
        return entry.getValue();
    }


    /**
     * Hash 算法，可自行实现 （生产环境 要用 更好 分布更均匀 的哈希函数）
     *
     */
    private int getHash(Object key) {
        return key.hashCode();
    }
}
