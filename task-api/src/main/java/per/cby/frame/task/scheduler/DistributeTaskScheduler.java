package per.cby.frame.task.scheduler;

import java.util.concurrent.TimeUnit;

/**
 * 分布式任务调度器
 * 
 * @author chenboyang
 *
 */
public interface DistributeTaskScheduler {

    /**
     * 任务调度
     * 
     * @param taskName 任务名称(名称必须唯一,不能和其它任务名称冲突)
     * @param job      任务作业
     */
    default void schedule(String taskName, Runnable job) {
        schedule(taskName, job, 10, TimeUnit.SECONDS);
    }

    /**
     * 任务调度
     * 
     * @param taskName 任务名称(名称必须唯一,不能和其它任务名称冲突)
     * @param job      任务作业
     * @param timeout  锁过期时间
     * @param unit     时间单位
     */
    void schedule(String taskName, Runnable job, long timeout, TimeUnit unit);

}
