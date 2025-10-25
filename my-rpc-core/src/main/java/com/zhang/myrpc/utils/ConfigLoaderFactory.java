package com.zhang.myrpc.utils;

import com.zhang.myrpc.utils.configLoaders.ConfigLoader;
import com.zhang.myrpc.utils.configLoaders.JsonConfigLoader;
import com.zhang.myrpc.utils.configLoaders.PropsConfigLoader;
import com.zhang.myrpc.utils.configLoaders.YamlConfigLoader;

/**
 * @author 30241
 */
public class ConfigLoaderFactory {
    public static ConfigLoader getLoader(String fileName) {
        if (fileName.endsWith(".properties")) {
            return new PropsConfigLoader();
        }
        if (fileName.endsWith(".yaml") || fileName.endsWith(".yml")) {
            return new YamlConfigLoader();
        }
        if (fileName.endsWith(".json")) {
            return new JsonConfigLoader();
        }
        throw new IllegalArgumentException("Unsupported config type: " + fileName);
    }
}
