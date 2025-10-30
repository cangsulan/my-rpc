package com.zhang.myrpc.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 30241
 * @version 1.0
 * @description: TODO
 * @date 2025/10/30 下午3:11
 */
public class Test {

    @org.junit.Test
    public void test() {
        String s1 = "123";
        Map<String,Object> map1 = new HashMap<String,Object>();
        Map<String,Object> map2 = new HashMap<String,Object>();
        Map<String,Object> map3 = new HashMap<String,Object>();
        map1.put("name","123");
        map2.put("name","123");
        map3.put("name","123");
        System.out.println(map1.hashCode());
        System.out.println(map2.hashCode());
        System.out.println(map3.hashCode());
    }
}
