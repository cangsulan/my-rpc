package com.zhang.myrpc.utils.configLoaders;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropsConfigLoader implements ConfigLoader {
    @Override
    public <T> T load(InputStream in, Class<T> tClass, String prefix) {
        try {
            Properties props = new Properties();
            props.load(new InputStreamReader(in, StandardCharsets.UTF_8)); // 可靠的 InputStream -> Properties

            // 将 Properties 转为嵌套 Map
            Map<String, Object> root = new HashMap<>();
            for (String key : props.stringPropertyNames()) {
                insertNested(root, key, props.getProperty(key));
            }

            // 提取 prefix 对应的部分
            Object section = root;
            if (prefix != null && root.containsKey(prefix)) {
                section = root.get(prefix);
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.convertValue(section, tClass);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties config", e);
        }
    }

    /** 把 app.name=value 这样的结构 转成 { app: { name: value } } */
    private void insertNested(Map<String, Object> root, String key, String value) {
        String[] parts = key.split("\\.");
        Map<String, Object> current = root;
        for (int i = 0; i < parts.length - 1; i++) {
            current = (Map<String, Object>) current.computeIfAbsent(parts[i], k -> new HashMap<>());
        }
        current.put(parts[parts.length - 1], value);
    }
}
