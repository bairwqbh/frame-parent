package per.cby.frame.ext.quartz.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 调度任务目标方法注解
 * 
 * @author chenboyang
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TriggerMethod {

    /**
     * 定时任务的cron表达式
     * 
     * @return cron表达式
     */
    String cron();

}
