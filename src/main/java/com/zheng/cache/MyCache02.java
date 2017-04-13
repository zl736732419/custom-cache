package com.zheng.cache;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用同步锁，采用读写锁将并发范围缩小到最小
 * Created by Administrator on 2017/4/13.
 */
public class MyCache02 {
    private final Map<String, Object> cache = Maps.newHashMap();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Computable c;
    
    public MyCache02(Computable c) {
        this.c = c;
    }
    
    public Object getValue(String key) {
        lock.readLock().lock();
        Object value;
        try {
            value = cache.get(key);
            if(null == value) {
                lock.readLock().unlock();
                lock.writeLock().unlock();
                try {
                    value = cache.get(key); //再次检查
                    if(null == value) {
                        value = c.compute(key);
                        cache.put(key, value);
                    }
                }finally {
                    lock.readLock().lock();
                    lock.writeLock().unlock(); //下降为更新锁
                }
            }
        } finally {
            lock.readLock().unlock();
        }
        
        return value;
    }
}
