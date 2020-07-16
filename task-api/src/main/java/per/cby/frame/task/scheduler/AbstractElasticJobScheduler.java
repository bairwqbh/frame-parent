package per.cby.frame.task.scheduler;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import per.cby.frame.task.annotation.ElasticJobTask;

/**
 * 抽象ElasticJob作业任务调度器
 * 
 * @author chenboyang
 *
 */
public abstract class AbstractElasticJobScheduler implements ElasticJob, ApplicationListener<ContextRefreshedEvent> {

    /** 是否初始化 */
    private boolean isInit = false;

    /** zookeeper注册中心 */
    @Autowired(required = false)
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ElasticJobTask elasticJobTask = elasticJobTask();
        if (zookeeperRegistryCenter == null || isInit || elasticJobTask == null) {
            return;
        }
        SpringJobScheduler scheduler = null;
        Class<? extends ElasticJobListener>[] listenerClass = elasticJobTask.listener();
        if (ArrayUtils.isNotEmpty(listenerClass)) {
            ElasticJobListener[] listeners = new ElasticJobListener[listenerClass.length];
            for (int i = 0; i < listenerClass.length; i++) {
                listeners[i] = event.getApplicationContext().getBean(listenerClass[i]);
            }
            scheduler = new SpringJobScheduler(this, zookeeperRegistryCenter, getLiteJobConfig(), listeners);
        } else {
            scheduler = new SpringJobScheduler(this, zookeeperRegistryCenter, getLiteJobConfig());
        }
        scheduler.init();
        isInit = true;
    }

    /**
     * 获取ElasticJob任务作业信息
     * 
     * @return ElasticJob任务作业信息
     */
    protected ElasticJobTask elasticJobTask() {
        return getClass().getAnnotation(ElasticJobTask.class);
    }

    /**
     * 获取类名称
     * 
     * @return 类名称
     */
    protected String className() {
        return getClass().getCanonicalName();
    }

    /**
     * 获取Lite作业配置
     * 
     * @return Lite作业配置
     */
    protected LiteJobConfiguration getLiteJobConfig() {
        return LiteJobConfiguration.newBuilder(getJobTypeConfig()).monitorExecution(elasticJobTask().monitorExecution())
                .maxTimeDiffSeconds(elasticJobTask().maxTimeDiffSeconds()).monitorPort(elasticJobTask().monitorPort())
                .jobShardingStrategyClass(elasticJobTask().jobShardingStrategyClass())
                .disabled(elasticJobTask().disabled()).overwrite(elasticJobTask().overwrite())
                .reconcileIntervalMinutes(elasticJobTask().reconcileIntervalMinutes()).build();
    }

    /**
     * 获取作业类型配置
     * 
     * @return 作业类型配置
     */
    protected abstract JobTypeConfiguration getJobTypeConfig();

    /**
     * 获取 作业核心配置
     * 
     * @return 作业核心配置
     */
    protected JobCoreConfiguration getCoreJobConfig() {
        return JobCoreConfiguration
                .newBuilder(elasticJobTask().name(), elasticJobTask().cron(), elasticJobTask().shardingTotalCount())
                .shardingItemParameters(elasticJobTask().shardingItemParameters())
                .jobParameter(elasticJobTask().jobParameter()).failover(elasticJobTask().failover())
                .misfire(elasticJobTask().misfire()).description(elasticJobTask().description()).build();
    }

}
