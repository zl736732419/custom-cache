==自定义实现缓存的几种方式
===synchronized
    虽然能实现线程安全访问，但是性能很低，每次都只能允许一个线程访问
===readwritelock
    通过读写锁将需要保证线程安全的线程块降低到最小
===通过并发容器实现concurrentMap+futureTask
    加入futureTask可以处理获取结果很漫长的情况
    
