package per.cby.frame.task.scheduler;

import com.dangdang.ddframe.job.api.script.ScriptJob;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;

/**
 * 脚本ElasticJob作业任务调度器
 * 
 * @author chenboyang
 *
 */
public abstract class ScriptElasticJobScheduler extends AbstractElasticJobScheduler implements ScriptJob {

    /**
     * 获取作业类型配置
     * 
     * @return 作业类型配置
     */
    @Override
    protected JobTypeConfiguration getJobTypeConfig() {
        return new ScriptJobConfiguration(getCoreJobConfig(), elasticJobTask().scriptCommandLine());
    }

}
