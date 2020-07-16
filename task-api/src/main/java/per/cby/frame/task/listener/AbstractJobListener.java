package per.cby.frame.task.listener;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import per.cby.frame.task.model.Task;
import per.cby.frame.task.service.TaskService;

import lombok.extern.slf4j.Slf4j;

/**
 * 分布式作业监听抽象类
 * 
 * @author chenboyang
 *
 */
@Slf4j
public abstract class AbstractJobListener extends AbstractDistributeOnceElasticJobListener {

    /** 默认执行开始任务超时时间(单位:毫秒) */
    protected static final long DEFAULT_START_TIMEOUT = 1000L * 60 * 10;

    /** 默认执行结束任务超时时间(单位:毫秒) */
    protected static final long DEFAULT_COMPLETE_TIMEOUT = 1000L * 60 * 10;

    /** 是否开启日志记录 */
    protected final boolean isLog;

    @Autowired(required = false)
    protected TaskService taskService;

    public AbstractJobListener() {
        this(true);
    }

    public AbstractJobListener(final boolean isLog) {
        this(DEFAULT_START_TIMEOUT, DEFAULT_COMPLETE_TIMEOUT, isLog);
    }

    public AbstractJobListener(final long startedTimeoutMilliseconds, final long completedTimeoutMilliseconds) {
        this(startedTimeoutMilliseconds, completedTimeoutMilliseconds, true);
    }

    public AbstractJobListener(final long startedTimeoutMilliseconds, final long completedTimeoutMilliseconds,
            final boolean isLog) {
        super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
        this.isLog = isLog;
    }

    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
        Task task = taskService.getDefault(shardingContexts.getJobName());
        if (task.getEndTime() == null) {
            LocalDateTime endTime = LocalDateTime.now();
            task.setEndTime(endTime);
            taskService.save(task);
        }
        if (isLog) {
            log.info("任务名称：{}，开始时间：{}，结束时间：{}，总分片数：{}，执行开始！", shardingContexts.getJobName(), task.getStartTime(),
                    task.getEndTime(), shardingContexts.getShardingTotalCount());
        }
    }

    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
        Task task = taskService.queryByName(shardingContexts.getJobName());
        if (task != null && task.getEndTime() != null) {
            taskService.setDeadline(task);
        }
        if (isLog) {
            log.info("任务名称：{}，开始时间：{}，结束时间：{}，总分片数：{}，执行结束！", shardingContexts.getJobName(), task.getStartTime(),
                    task.getEndTime(), shardingContexts.getShardingTotalCount());
        }
    }

}
