package com.zheng.cache;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 性能低下，任何情况下都只有一个线程能访问
 * Created by Administrator on 2017/4/13.
 */
public class MyCache01 {
    private final Map<String, Object> cache = Maps.newHashMap();
    private final Computable c;
    
    public MyCache01(Computable c) {
        this.c = c;
    }
    
    public synchronized Object getValue(String key) {
        Object value = cache.get(key);
        if(null == value) {
            value = c.compute(key);
            cache.put(key, value);
        }
        
        return value;
    }
    
}
