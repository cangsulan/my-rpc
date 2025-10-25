package com.zhang.myrpc.utils.configLoaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.InputStream;
import java.util.Map;

/**
 * @author 30241
 */
@SuppressWarnings("unchecked")
public class YamlConfigLoader implements ConfigLoader {
    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    @Override
    public <T> T load(InputStream in, Class<T> tClass, String prefix) {
        try {
            Map<String, Object> root = yamlMapper.readValue(in, Map.class);
            Object section = root;
            if (prefix != null && root != null && root.containsKey(prefix)) {
                section = root.get(prefix);
            }
            return yamlMapper.convertValue(section, tClass);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load YAML config", e);
        }
    }
}
