package per.cby.frame.mqtt.constant;

/**
 * MQTT常量
 * 
 * @author chenboyang
 * @since 2020年2月15日
 *
 */
public interface MqttConstant {

    /** 前缀 */
    String PREFIX = "mqtt_";

    /** 服务质量 */
    String QOS = PREFIX + "qos";

    /** 接收服务质量 */
    String RECEIVED_QOS = PREFIX + "receivedQos";

    /** 重发 */
    String DUPLICATE = PREFIX + "duplicate";

    /** 保留 */
    String RETAINED = PREFIX + "retained";

    /** 接收保留 */
    String RECEIVED_RETAINED = PREFIX + "receivedRetained";

    /** 主题 */
    String TOPIC = PREFIX + "topic";

    /** 接收主题 */
    String RECEIVED_TOPIC = PREFIX + "receivedTopic";

    /** 只发送一次，不保证发送成功 */
    int QOS_0 = 0;

    /** 发送并保证成功接收，不保证重复发送 */
    int QOS_1 = 1;

    /** 发送并保证成功接收，保证只发送一次 */
    int QOS_2 = 2;

}
