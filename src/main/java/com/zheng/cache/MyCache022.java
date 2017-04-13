package com.zheng.cache;

import com.google.common.collect.Maps;

import java.util.concurrent.*;

/**
 * 不使用读写锁，采用并发容器实现,
 * 这里使用Future来代替，主要是考虑到获取值的过程可能很漫长
 * 采用futuretask,当结果正在计算中，就等待，如果结果计算完成，就直接返回
 * Created by Administrator on 2017/4/13.
 */
public class MyCache022 {
    private final ConcurrentMap<String, Future<Object>> cache = Maps.newConcurrentMap();
    private final Computable c;
    
    public MyCache022(Computable c) {
        this.c = c;
    }
    
    public Object getValue(final String key) {
        Future<Object> future = cache.get(key);
        if(null == future) {
            FutureTask<Object> futureTask = new FutureTask<Object>(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return c.compute(key);
                }
            });
            
            future = cache.putIfAbsent(key, futureTask); //这里保证了多线程并发安全访问
            if(null == future) { //这里只有第一次会返回null
                future = futureTask; //这里将指向第一个创建的对象
                futureTask.run(); //开始计算，保证只计算一次
            }
        }
        
        try {
            return future.get();
        } catch (CancellationException e) {
            cache.remove(key);
        } catch(ExecutionException e1) {
            cache.remove(key);
        } catch (InterruptedException e) {
            cache.remove(key);
        }
        
        return null;
    }
    
}
