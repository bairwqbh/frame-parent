package per.cby.frame.mqtt;

import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;

/**
 * 基础MQTT消息发布者
 * 
 * @author chenboyang
 * @since 2019年11月6日
 *
 */
public interface BaseMqttPublisher {

    /**
     * 发布消息
     * 
     * @param topic   主题
     * @param payload 消息载荷
     */
    void publish(@Header(MqttHeaders.TOPIC) String topic, byte[] payload);

    /**
     * 发布消息
     * 
     * @param topic   主题
     * @param qos     服务质量
     * @param payload 消息载荷
     */
    void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, byte[] payload);

    /**
     * 发布消息
     * 
     * @param headers 头部信息
     * @param payload 消息载荷
     */
    void publish(@Headers MessageHeaders headers, byte[] payload);

    /**
     * 发布消息
     * 
     * @param topic   主题
     * @param payload 消息载荷
     */
    void publish(@Header(MqttHeaders.TOPIC) String topic, String payload);

    /**
     * 发布消息
     * 
     * @param topic   主题
     * @param qos     服务质量
     * @param payload 消息载荷
     */
    void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);

    /**
     * 发布消息
     * 
     * @param headers 头部信息
     * @param payload 消息载荷
     */
    void publish(@Headers MessageHeaders headers, String payload);

}
