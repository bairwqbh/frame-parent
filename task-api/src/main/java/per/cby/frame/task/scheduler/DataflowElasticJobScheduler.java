package per.cby.frame.task.scheduler;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;

/**
 * 数据流ElasticJob作业任务调度器
 * 
 * @author chenboyang
 * 
 * @param <T> 数据类型
 */
public abstract class DataflowElasticJobScheduler<T> extends AbstractElasticJobScheduler implements DataflowJob<T> {

    @Override
    protected JobTypeConfiguration getJobTypeConfig() {
        return new DataflowJobConfiguration(getCoreJobConfig(), className(), elasticJobTask().streamingProcess());
    }

}
