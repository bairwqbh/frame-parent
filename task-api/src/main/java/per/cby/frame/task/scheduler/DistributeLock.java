package per.cby.frame.task.scheduler;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁接口
 * 
 * @author chenboyang
 *
 */
public interface DistributeLock {

    /**
     * 获取锁
     * 
     * @param key     业务键
     * @param timeout 锁过期时间
     * @param unit    时间单位
     * @return 是否成功
     */
    boolean acquire(String key, long timeout, TimeUnit unit);

    /**
     * 释放锁
     * 
     * @param key 业务键
     */
    void release(String key);

}
