package per.cby.frame.redis.task;

import java.util.concurrent.TimeUnit;

import per.cby.frame.redis.annotation.RedisStorage;
import per.cby.frame.redis.storage.value.DefaultCatalogRedisValueStorage;
import per.cby.frame.task.scheduler.DistributeLock;

import lombok.Synchronized;

/**
 * 定时任务调度Redis分布式锁存储
 * 
 * @author chenboyang
 *
 */
@RedisStorage("task:job:lock:")
public class TaskJobRedisLock extends DefaultCatalogRedisValueStorage<Object> implements DistributeLock {

    @Override
    @Synchronized
    public boolean acquire(String key, long timeout, TimeUnit unit) {
        boolean lock = setIfAbsent(key, timeout + ":" + unit.toString());
        if (lock) {
            template().expire(assembleKey(key), timeout, unit);
        }
        return lock;
    }

    @Override
    @Synchronized
    public void release(String key) {
        if (has(key)) {
            delete(key);
        }
    }

}
