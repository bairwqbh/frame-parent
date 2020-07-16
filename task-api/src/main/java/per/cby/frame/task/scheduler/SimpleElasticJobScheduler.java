package per.cby.frame.task.scheduler;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;

/**
 * 简单ElasticJob作业任务调度器
 * 
 * @author chenboyang
 *
 */
public abstract class SimpleElasticJobScheduler extends AbstractElasticJobScheduler implements SimpleJob {

    /**
     * 获取作业类型配置
     * 
     * @return 作业类型配置
     */
    @Override
    protected JobTypeConfiguration getJobTypeConfig() {
        return new SimpleJobConfiguration(getCoreJobConfig(), className());
    }

}
