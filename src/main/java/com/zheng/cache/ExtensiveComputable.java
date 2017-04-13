package com.zheng.cache;

/**
 * 这里是模拟一个简单的数据获取方式
 * Created by Administrator on 2017/4/13.
 */
public class ExtensiveComputable implements Computable {
    @Override
    public Object compute(String key) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello world";
    }
}
