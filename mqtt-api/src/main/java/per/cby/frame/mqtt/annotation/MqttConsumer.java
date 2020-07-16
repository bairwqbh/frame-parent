package per.cby.frame.mqtt.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.codec.CharEncoding;

/**
 * MQTT消费者配置
 * 
 * @author chenboyang
 * @since 2019年6月27日
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MqttConsumer {

    /**
     * Bean名称
     * 
     * @return Bean名称
     */
    String name() default "";

    /**
     * 客户端编号
     * 
     * @return 客户端编号
     */
    String clientId() default "";

    /**
     * 客户端构建工厂名称
     * 
     * @return 客户端构建工厂名称
     */
    String clientFactory() default "";

    /**
     * 主题名称
     * 
     * @return 主题名称
     */
    String[] topic();

    /**
     * 服务质量
     * 
     * @return 服务质量
     */
    int[] qos() default {};

    /**
     * 字符集编码
     * 
     * @return 字符集编码
     */
    String charset() default CharEncoding.UTF_8;

}
