package per.cby.frame.rabbitmq;

/**
 * RabbitMQ常用交换机名称类
 * 
 * @author chenboyang
 *
 */
public interface RabbitMQExchangeNames {

    /** 定向 */
    String DIRECT = "amq.direct";

    /** 广播 */
    String FANOUT = "amq.fanout";

    /** 头部信息 */
    String HEADERS = "amq.headers";

    /** 匹配 */
    String MATCH = "amq.match";

    /** 日志 */
    String LOG = "amq.rabbitmq.log";

    /** 追踪 */
    String TRACE = "amq.rabbitmq.trace";

    /** 主题 */
    String TOPIC = "amq.topic";

}
