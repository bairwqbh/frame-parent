package per.cby.frame.task.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import per.cby.frame.task.config.properties.ElasticJobProperties;
import per.cby.frame.task.constant.Constant;
import per.cby.frame.task.listener.SimpleJobListener;
import per.cby.frame.task.mongo.TaskMongo;
import per.cby.frame.task.service.TaskService;
import per.cby.frame.task.service.impl.TaskServiceImpl;

/**
 * 分布式任务调度配置
 * 
 * @author chenboyang
 *
 */
@Configuration("__ELASTIC_JOB_CONFIG__")
@ConditionalOnProperty("spring.elasticjob.serverlists")
@ConditionalOnClass(name = "com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter")
public class ElasticJobConfig implements Constant {

    /**
     * 获取zookeeper注册中心配置
     * 
     * @return zookeeper注册中心配置
     */
    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties("spring.elasticjob")
    public ElasticJobProperties elasticJobProperties() {
        return new ElasticJobProperties();
    }

    /**
     * 获取zookeeper注册中心
     * 
     * @return zookeeper注册中心
     */
    @Bean(initMethod = "init")
    @ConditionalOnMissingBean
    public ZookeeperRegistryCenter zookeeperRegistryCenter() {
        return new ZookeeperRegistryCenter(elasticJobProperties().config());
    }

    /**
     * 任务调度信息Mongo存储
     * 
     * @return 任务调度信息Mongo存储
     */
    @Bean(TASK_MONGO_BEAN)
    @ConditionalOnMissingBean(name = TASK_MONGO_BEAN)
    @ConditionalOnClass(name = "per.cby.frame.mongo.storage.MongoDBStorage")
    public TaskMongo taskMongo() {
        return new TaskMongo();
    }

    /**
     * 任务调度信息服务
     * 
     * @return 任务调度信息服务
     */
    @Bean(TASK_SERVICE_BEAN)
    @ConditionalOnMissingBean
    @ConditionalOnClass(name = "per.cby.frame.mongo.storage.MongoDBStorage")
    public TaskService TaskService() {
        return new TaskServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public SimpleJobListener simpleJobListener() {
        return new SimpleJobListener();
    }

}
