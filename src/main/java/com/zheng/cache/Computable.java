package com.zheng.cache;

/**
 * 这里模拟构造数据接口，当前对象需要经过一系列运算后才能得到,
 * 假设获取对象的过程很漫长
 * Created by Administrator on 2017/4/13.
 */
public interface Computable {
    Object compute(String key);
}
