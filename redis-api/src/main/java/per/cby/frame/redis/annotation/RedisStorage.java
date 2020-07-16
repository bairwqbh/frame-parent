package per.cby.frame.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Redis存储模型注解
 * 
 * @author chenboyang
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisStorage {

    /**
     * Redis存储键或目录键前缀
     * 
     * @return 键
     */
    String value();

    /**
     * 指定缺省的键类型
     * 
     * @return 类型
     */
    Class<?> keyClass() default String.class;

    /**
     * 指定缺省的值类型
     * 
     * @return 类型
     */
    Class<?> valueClass() default Object.class;

    /**
     * Redis处理器Bean名称
     * 
     * @return 处理器Bean名称
     */
    String handler() default "";

}
