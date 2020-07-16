package per.cby.frame.task.scheduler;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import per.cby.frame.common.exception.BusinessAssert;

import lombok.extern.slf4j.Slf4j;

/**
 * 简单分布式任务调度器
 * 
 * @author chenboyang
 *
 */
@Slf4j
public class SimpleDistributeTaskScheduler implements DistributeTaskScheduler {

    /** 任务最短过期时间 */
    private final long MIN_TIME = 1000L;

    @Autowired(required = false)
    private DistributeLock distributeLock;

    @Override
    public void schedule(String taskName, Runnable job, long timeout, TimeUnit unit) {
        BusinessAssert.hasText(taskName, "任务名称不能为空！");
        BusinessAssert.notNull(job, "任务执行体不能为空！");
        boolean lock = false;
        try {
            lock = distributeLock.acquire(taskName, timeout, unit);
            if (lock) {
                long stime = System.currentTimeMillis();
                job.run();
                long etime = System.currentTimeMillis();
                long dtime = etime - stime;
                if (dtime < MIN_TIME) {
                    TimeUnit.MILLISECONDS.sleep(MIN_TIME - dtime);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (lock) {
                distributeLock.release(taskName);
            }
        }
    }

}
