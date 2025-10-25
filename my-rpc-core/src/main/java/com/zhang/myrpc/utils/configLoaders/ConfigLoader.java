package com.zhang.myrpc.utils.configLoaders;

import java.io.InputStream;

/**
 * @author 30241
 */
public interface ConfigLoader {

    <T> T load(InputStream in, Class<T> tClass, String prefix);
}
