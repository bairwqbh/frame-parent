package per.cby.frame.common.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import per.cby.frame.common.util.ThreadPoolUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 异步任务执行配置
 * 
 * @author chenboyang
 *
 */
@Slf4j
@Configuration("__ASYNC_EXECUTE_CONFIG__")
@ConditionalOnMissingBean(AsyncConfigurer.class)
@ConditionalOnBean(annotation = EnableAsync.class)
@ConditionalOnClass(name = "org.springframework.scheduling.annotation.AsyncConfigurer")
public class AsyncExecuteConfig implements AsyncConfigurer {

    /** 核心线程数 */
    public static final int CORE_POOL_SIZE = ThreadPoolUtil.CORE_POOL_SIZE;

    /** 最大线程数 */
    public static final int MAX_POOL_SIZE = ThreadPoolUtil.MAX_POOL_SIZE;

    /** 队列容量 */
    public static final int QUEUE_CAPACITY = ThreadPoolUtil.MIX_POOL_SIZE;

    /** 线程空闲秒数 */
    public static final int KEEP_ALIVE_SECONDS = 60;

    /** 线程名称前缀 */
    public static final String THREAD_NAME_PREFIX = "async-task-";

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.setRejectedExecutionHandler(new CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> log.error(throwable.getMessage(), throwable);
    }

}
