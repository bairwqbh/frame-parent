package per.cby.frame.mqtt;

import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;

import per.cby.frame.common.util.JsonUtil;

import lombok.RequiredArgsConstructor;

/**
 * MQTT消息发布者适配器
 * 
 * @author chenboyang
 * @since 2019年11月28日
 *
 */
@RequiredArgsConstructor
public class MqttPublisherAdapter implements BaseMqttPublisher {

    /** MQTT消息发布者 */
    private final BaseMqttPublisher mqttPublisher;

    @Override
    public void publish(String topic, byte[] payload) {
        mqttPublisher.publish(topic, payload);
    }

    @Override
    public void publish(String topic, int qos, byte[] payload) {
        mqttPublisher.publish(topic, qos, payload);
    }

    @Override
    public void publish(MessageHeaders headers, byte[] payload) {
        mqttPublisher.publish(headers, payload);
    }

    @Override
    public void publish(String topic, String payload) {
        mqttPublisher.publish(topic, payload);
    }

    @Override
    public void publish(String topic, int qos, String payload) {
        mqttPublisher.publish(topic, qos, payload);
    }

    @Override
    public void publish(MessageHeaders headers, String payload) {
        mqttPublisher.publish(headers, payload);
    }

    /**
     * 发布消息
     * 
     * @param topic   主题
     * @param payload 消息载荷
     */
    public void publish(@Header(MqttHeaders.TOPIC) String topic, Object payload) {
        publish(topic, JsonUtil.toJson(payload));
    }

    /**
     * 发布消息
     * 
     * @param topic   主题
     * @param qos     服务质量
     * @param payload 消息载荷
     */
    public void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, Object payload) {
        publish(topic, qos, JsonUtil.toJson(payload));
    }

    /**
     * 发布消息
     * 
     * @param headers 头部信息
     * @param payload 消息载荷
     */
    public void publish(@Headers MessageHeaders headers, Object payload) {
        publish(headers, JsonUtil.toJson(payload));
    }

}
