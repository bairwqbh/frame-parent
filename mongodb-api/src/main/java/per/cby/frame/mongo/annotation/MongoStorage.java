package per.cby.frame.mongo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MongoDB存储集合注解
 * 
 * @author chenboyang
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MongoStorage {

    /**
     * 存储集合名称
     * 
     * @return 名称
     */
    String name();

    /**
     * 存储对象类型
     * 
     * @return 类型
     */
    Class<?> type() default void.class;

    /**
     * 处理器Bean名称
     * 
     * @return 处理器Bean名称
     */
    String handler() default "";

}
