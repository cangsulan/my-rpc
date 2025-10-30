package com.zhang.myrpc.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * 服务元信息（注册信息）
 * @author 30241
 */

@Data
public class ServiceMetaInfo {


    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务版本号
     */
    private String serviceVersion = "1.0";

    /**
     * 服务域名
     */
    private String serviceHost;

    /**
     * 服务端口号
     */
    private Integer servicePort;

    /**
     * 服务分组（暂未实现）
     */
    private String serviceGroup = "default";


    /**
     * 获取服务键名
     *
     * @return
     */
    public String getServiceKey() {
        // 后续可扩展服务分组
        // return String.format("%s:%s:%s", serviceName, serviceVersion, serviceGroup);
        return String.format("%s:%s", serviceName, serviceVersion);
    }

    /**
     * 获取服务注册节点键名
     *
     * @return
     */
    public String getServiceNodeKey() {
        return String.format("%s/%s:%s", getServiceKey(), serviceHost, servicePort);
    }

    /**
     * 获取完整服务地址
     *
     * @return
     */
    public String getServiceAddress() {
        if (!StrUtil.contains(serviceHost, "http")) {
            return String.format("http://%s:%s", serviceHost, servicePort);
        }
        return String.format("%s:%s", serviceHost, servicePort);
    }

    // 工具方法，把 serviceNodeKey 转为 serviceKey
    public static String nodeKeyToServiceKey(String serviceNodeKey) {
        if (StrUtil.isBlank(serviceNodeKey)) {
            return null;
        }
        // 1. 找到最后一个冒号 ':' 的位置，它分隔了主机和端口
        int lastColonIndex = serviceNodeKey.lastIndexOf(':');
        // 2. 找到最后一个斜杠 '/' 的位置，它分隔了serviceKey和主机
        int lastSlashIndex = serviceNodeKey.lastIndexOf('/');

        // 进行基本的格式校验
        if (lastSlashIndex == -1 || lastColonIndex == -1 || lastColonIndex < lastSlashIndex) {
            // 如果格式不符合预期，可以返回空字符串或抛出异常
            return null;
        }

        // 3. 截取从开头到最后一个斜杠之前的部分，这就是我们需要的serviceKey
        return serviceNodeKey.substring(0, lastSlashIndex);
    }
}
