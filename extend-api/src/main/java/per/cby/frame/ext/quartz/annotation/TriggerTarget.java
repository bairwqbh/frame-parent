package per.cby.frame.ext.quartz.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import per.cby.frame.common.spring.SpringBean;

/**
 * 调度任务目标类注解
 * 
 * @author chenboyang
 *
 */
@SpringBean
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TriggerTarget {

    /**
     * SpringBean名称
     * 
     * @return SpringBean名称
     */
    String value() default "";

}
