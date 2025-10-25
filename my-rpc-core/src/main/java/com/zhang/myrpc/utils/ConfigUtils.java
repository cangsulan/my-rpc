package com.zhang.myrpc.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.myrpc.utils.configLoaders.ConfigLoader;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * 配置工具类，把 指定的配置文件 转化为 指定类的配置类
 */
public class ConfigUtils {

    /**
     * 加载配置对象
     *
     * @param tClass
     * @param prefix
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    /**
     * 加载配置对象，支持区分环境
     *
     * @param tClass
     * @param prefix
     * @param environment
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        String baseName = "application";
        String[] extensions = {".yaml",".yml",".properties", ".json"};

        // 优先加载带 environment 的文件
        for (String ext : extensions) {
            String envFile = baseName + (StrUtil.isNotBlank(environment) ? "-" + environment : "") + ext;
            T result = tryLoad(envFile, tClass, prefix);
            if (result != null) {
                return result;
            }
        }

        // 否则加载默认文件
        for (String ext : extensions) {
            String defaultFile = baseName + ext;
            T result = tryLoad(defaultFile, tClass, prefix);
            if (result != null) {
                return result;
            }
        }

        throw new RuntimeException("No configuration file found for base name: " + baseName);
    }

    private static <T> T tryLoad(String fileName, Class<T> tClass, String prefix) {
        try (InputStream in = ResourceUtil.getStream(fileName)) {
            if (in == null) {
                // 文件不存在
                return null;
            }
            ConfigLoader loader = ConfigLoaderFactory.getLoader(fileName);
            return loader.load(in, tClass, prefix);
        } catch (Exception e) {
            return null;
        }
    }
}
