package per.cby.frame.task.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import per.cby.frame.task.scheduler.DistributeTaskScheduler;
import per.cby.frame.task.scheduler.SimpleDistributeTaskScheduler;

/**
 * 任务调度配置
 * 
 * @author chenboyang
 * @since 2020年7月13日
 *
 */
@Configuration("__TASK_CONFIG__")
public class TaskConfig {

    /**
     * 分布式任务调度器
     * 
     * @return 分布式任务调度器
     */
    @Bean
    @ConditionalOnMissingBean
    public DistributeTaskScheduler distributeTaskScheduler() {
        return new SimpleDistributeTaskScheduler();
    }

}
