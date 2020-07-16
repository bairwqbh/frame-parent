package per.cby.frame.task.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.dangdang.ddframe.job.api.JobType;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

/**
 * ElasticJob任务作业注解
 * 
 * @author chenboyang
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticJobTask {

    /**
     * 获取作业名称
     * 
     * @return 作业名称
     */
    String name();

    /**
     * 获取cron表达式
     * 
     * @return cron表达式
     */
    String cron();

    /**
     * 获取作业分片总数
     * 
     * @return 作业分片总数
     */
    int shardingTotalCount() default 1;

    /**
     * 获取分片项参数
     * 
     * @return 分片项参数
     */
    String shardingItemParameters() default "";

    /**
     * 获取作业自定义参数
     * 
     * @return 作业自定义参数
     */
    String jobParameter() default "";

    /**
     * 是否开启任务执行失效转移
     * 
     * @return 是否开启任务执行失效转移
     */
    boolean failover() default false;

    /**
     * 是否开启错过任务重新执行
     * 
     * @return 是否开启错过任务重新执行
     */
    boolean misfire() default true;

    /**
     * 获取作业描述信息
     * 
     * @return 作业描述信息
     */
    String description() default "";

    /**
     * 获取作业类型
     * 
     * @return 作业类型
     */
    JobType jobType() default JobType.SIMPLE;

    /**
     * 获取是否流式处理数据
     * 
     * @return 是否流式处理数据
     */
    boolean streamingProcess() default false;

    /**
     * 获取脚本型作业执行命令行
     * 
     * @return 脚本型作业执行命令行
     */
    String scriptCommandLine() default "";

    /**
     * 获取监控作业运行时状态
     * 
     * @return 监控作业运行时状态
     */
    boolean monitorExecution() default true;

    /**
     * 获取最大允许的本机与注册中心的时间误差秒数
     * 
     * @return 最大允许的本机与注册中心的时间误差秒数
     */
    int maxTimeDiffSeconds() default -1;

    /**
     * 获取作业监控端口
     * 
     * @return 作业监控端口
     */
    int monitorPort() default -1;

    /**
     * 获取作业分片策略实现类全路径
     * 
     * @return 作业分片策略实现类全路径
     */
    String jobShardingStrategyClass() default "";

    /**
     * 获取作业是否禁止启动
     * 
     * @return 作业是否禁止启动
     */
    boolean disabled() default false;

    /**
     * 获取本地配置是否可覆盖注册中心配置
     * 
     * @return 本地配置是否可覆盖注册中心配置
     */
    boolean overwrite() default true;

    /**
     * 获取修复作业服务器不一致状态服务调度间隔时间
     * 
     * @return 修复作业服务器不一致状态服务调度间隔时间
     */
    int reconcileIntervalMinutes() default 10;

    /**
     * 获取任务监听器类
     * 
     * @return 任务监听器类
     */
    Class<? extends ElasticJobListener>[] listener() default {};

}
