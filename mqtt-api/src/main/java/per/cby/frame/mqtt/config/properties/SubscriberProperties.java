package per.cby.frame.mqtt.config.properties;

import java.nio.charset.Charset;

import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * MQTT订阅配置属性
 * 
 * @author chenboyang
 * @since 2020年4月30日
 *
 */
@Data
@Accessors(chain = true)
public class SubscriberProperties {

    /** 对象名称 */
    private String name;

    /** 客户端编号 */
    private String clientId;

    /** 连接工厂 */
    private MqttPahoClientFactory clientFactory;

    /** 主题 */
    private String[] topic;

    /** 服务质量 */
    private int[] qos;

    /** 字符集编码 */
    private Charset charset;

}
