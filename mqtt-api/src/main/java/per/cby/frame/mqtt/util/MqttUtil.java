package per.cby.frame.mqtt.util;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.integration.mqtt.support.MqttHeaders;

import per.cby.frame.mqtt.MqttPublisherAdapter;

import lombok.experimental.UtilityClass;

/**
 * MQTT帮助类
 * 
 * @author chenboyang
 * @since 2019年12月6日
 *
 */
@UtilityClass
public class MqttUtil {

    /**
     * 消息发布
     * 
     * @param mqttPublisher 发布服务
     * @param topic         主题
     * @param payload       载荷
     * @param header        头部信息
     */
    public void publish(MqttPublisherAdapter mqttPublisher, String topic, byte[] payload, Map<String, Object> header) {
        if (MapUtils.isNotEmpty(header) && header.containsKey(MqttHeaders.QOS) && header.size() == 1) {
            mqttPublisher.publish(topic, (int) header.get(MqttHeaders.QOS), payload);
        } else {
            mqttPublisher.publish(topic, payload);
        }
    }

    /**
     * 消息发布
     * 
     * @param mqttPublisher 发布服务
     * @param topic         主题
     * @param payload       载荷
     * @param header        头部信息
     */
    public void publish(MqttPublisherAdapter mqttPublisher, String topic, String payload, Map<String, Object> header) {
        if (MapUtils.isNotEmpty(header) && header.containsKey(MqttHeaders.QOS) && header.size() == 1) {
            mqttPublisher.publish(topic, (int) header.get(MqttHeaders.QOS), payload);
        } else {
            mqttPublisher.publish(topic, payload);
        }
    }

    /**
     * 消息发布
     * 
     * @param mqttPublisher 发布服务
     * @param topic         主题
     * @param payload       载荷
     * @param header        头部信息
     */
    public void publish(MqttPublisherAdapter mqttPublisher, String topic, Object payload, Map<String, Object> header) {
        if (MapUtils.isNotEmpty(header) && header.containsKey(MqttHeaders.QOS) && header.size() == 1) {
            mqttPublisher.publish(topic, (int) header.get(MqttHeaders.QOS), payload);
        } else {
            mqttPublisher.publish(topic, payload);
        }
    }

}
