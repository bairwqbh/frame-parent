package per.cby.frame.kafka.annotation;

/**
 * kafka消息发送器注解
 * 
 * @author chenboyang
 *
 */
public @interface KafkaProducer {

    /**
     * 获取操作模块
     * 
     * @return 操作模块
     */
    String template() default "";

    /**
     * 获取主题
     * 
     * @return 主题
     */
    String topic() default "";

}
