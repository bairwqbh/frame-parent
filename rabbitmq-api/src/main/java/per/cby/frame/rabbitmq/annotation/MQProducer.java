package per.cby.frame.rabbitmq.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MQ消息生产者注解
 * 
 * @author chenboyang
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MQProducer {

    /**
     * 交换机名称
     * 
     * @return 交换机名称
     */
    String exchange();

    /**
     * 路由key
     * 
     * @return 路由key
     */
    String routingKey() default "";

    /**
     * MQ操作模板
     * 
     * @return 操作模板
     */
    String rabbitTemplate() default "";

}
