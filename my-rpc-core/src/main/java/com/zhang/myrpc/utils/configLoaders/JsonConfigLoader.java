package com.zhang.myrpc.utils.configLoaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.InputStream;
import java.util.Map;

@SuppressWarnings("unchecked")
public class JsonConfigLoader implements ConfigLoader {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T load(InputStream in, Class<T> tClass, String prefix) {
        try {
            Map<String, Object> root = mapper.readValue(in, Map.class);
            Object section = root;
            if (prefix != null && root != null && root.containsKey(prefix)) {
                section = root.get(prefix);
            }
            return mapper.convertValue(section, tClass);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON config", e);
        }
    }
}
