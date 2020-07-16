package per.cby.frame.mqtt.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;

import per.cby.frame.common.util.IDUtil;
import per.cby.frame.mqtt.BaseMqttPublisher;
import per.cby.frame.mqtt.MqttPublisherAdapter;
import per.cby.frame.mqtt.config.properties.MqttProperties;

/**
 * MQTT自动配置
 * 
 * @author chenboyang
 * @since 2019年6月27日
 *
 */
@IntegrationComponentScan
@Configuration("__MQTT_CONFIG__")
@ConditionalOnProperty("spring.mqtt.serverURIs")
@ConditionalOnClass(name = { "org.eclipse.paho.client.mqttv3.MqttConnectOptions",
        "org.springframework.integration.mqtt.core.MqttPahoClientFactory" })
public class MqttConfig {

    /**
     * MQTT配置属性
     * 
     * @return MQTT配置属性
     */
    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties("spring.mqtt")
    public MqttProperties mqttProperties() {
        return new MqttProperties();
    }

    /**
     * MQTT客户端构建工厂
     * 
     * @return MQTT客户端构建工厂
     */
    @Bean
    @ConditionalOnMissingBean
    public MqttPahoClientFactory mqttPahoClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(mqttProperties());
        return factory;
    }

    /**
     * MQTT输出通道
     * 
     * @return MQTT输出通道
     */
    @Bean
    @ConditionalOnMissingBean(name = "mqttOutputChannel")
    public DirectChannel mqttOutputChannel() {
        return new DirectChannel();
    }

    /**
     * MQTT输出消息处理器
     * 
     * @return MQTT输出消息处理器
     */
    @Bean
    @ConditionalOnMissingBean
    @ServiceActivator(inputChannel = "mqttOutputChannel")
    public MqttPahoMessageHandler mqttOutput() {
        if (StringUtils.isBlank(mqttProperties().getClientId())) {
            mqttProperties().setClientId(IDUtil.createUUID32());
        }
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(mqttProperties().getClientId(), mqttPahoClientFactory());
        handler.setAsync(mqttProperties().isAsync());
        handler.setDefaultTopic(mqttProperties().getDefaultTopic());
        return handler;
    }

    /**
     * MQTT消息发布者
     * 
     * @author chenboyang
     * @since 2019年6月27日
     *
     */
    @MessagingGateway(defaultRequestChannel = "mqttOutputChannel")
    public interface MqttPublisher extends BaseMqttPublisher {

    }

    /**
     * MQTT消息发布者适配器
     * 
     * @param mqttPublisher MQTT消息发布者
     * @return MQTT消息发布者适配器
     */
    @Bean
    @ConditionalOnMissingBean(name = "mqttPublisherAdapter")
    public MqttPublisherAdapter mqttPublisherAdapter(MqttPublisher mqttPublisher) {
        return new MqttPublisherAdapter(mqttPublisher);
    }

}
