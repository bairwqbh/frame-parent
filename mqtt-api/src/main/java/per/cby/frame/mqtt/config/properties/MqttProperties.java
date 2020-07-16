package per.cby.frame.mqtt.config.properties;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * MQTT配置属性
 * 
 * @author chenboyang
 * @since 2019年6月27日
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class MqttProperties extends MqttConnectOptions {

    /** 客户端编号 */
    private String clientId;

    /** 是否异步 */
    private boolean async = true;

    /** 默认topic */
    private String defaultTopic = "default";

}
