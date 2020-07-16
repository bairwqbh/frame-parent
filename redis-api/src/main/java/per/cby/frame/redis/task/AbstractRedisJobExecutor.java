package per.cby.frame.redis.task;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import per.cby.frame.task.scheduler.DistributeTaskScheduler;

import lombok.extern.slf4j.Slf4j;

/**
 * 任务执行器抽象类
 * 
 * @author chenboyang
 *
 */
@Slf4j
public abstract class AbstractRedisJobExecutor {

    @Autowired(required = false)
    protected DistributeTaskScheduler distributeTaskScheduler;

    /** Redis到数据访问层执行器 */
    private static volatile RedisToMapperExecutor redisToMapperExecutor;

    /**
     * 分布式任务调度
     * 
     * @param taskName 任务名称(名称必须唯一,不能和其它任务名称冲突)
     * @param job      任务节点
     */
    protected void distributeScheduler(String taskName, Runnable job) {
        Optional.ofNullable(distributeTaskScheduler)
                .ifPresent(distributeTaskScheduler -> distributeTaskScheduler.schedule(taskName, job));
    }

    /**
     * 分布式任务调度
     * 
     * @param taskName 任务名称(名称必须唯一,不能和其它任务名称冲突)
     * @param job      任务节点
     * @param timeout  锁过期时间
     * @param unit     时间单位
     */
    protected void distributeScheduler(String taskName, Runnable job, long timeout, TimeUnit unit) {
        Optional.ofNullable(distributeTaskScheduler)
                .ifPresent(distributeTaskScheduler -> distributeTaskScheduler.schedule(taskName, job, timeout, unit));
    }

    /**
     * Redis到数据访问层执行器
     * 
     * @return 执行器
     */
    public static RedisToMapperExecutor redisToMapperExecutor() {
        return Optional.ofNullable(redisToMapperExecutor)
                .orElseGet(() -> redisToMapperExecutor = new RedisToMapperExecutorImpl());
    }

    /**
     * 单向任务操作日志记录
     * 
     * @param sourceType 源类型
     * @param souceName  源名称
     * @param operate    操作名称
     * @param num        操作记录数
     */
    protected void singleOperateLog(String sourceType, String souceName, String operate, int num) {
        StringBuilder sb = new StringBuilder();
        sb.append("从").append(sourceType).append("‘").append(souceName).append("’").append("已").append(operate)
                .append(num).append("条数据。");
        log.info(sb.toString());
    }

    /**
     * 双向任务操作日志记录
     * 
     * @param sourceType      源类型
     * @param souceName       源名称
     * @param operate         操作名称
     * @param num             操作记录数
     * @param destinationType 目标类型
     * @param destinationName 目标名称
     */
    protected void doubleOperateLog(String sourceType, String souceName, String operate, int num,
            String destinationType, String destinationName) {
        StringBuilder sb = new StringBuilder();
        sb.append("从").append(sourceType).append("‘").append(souceName).append("’").append("已").append(operate)
                .append(num).append("条数据到").append(destinationType).append("‘").append(destinationName).append("’。");
        log.info(sb.toString());
    }

}
