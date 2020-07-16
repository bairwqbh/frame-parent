package per.cby.frame.mqtt;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;

import per.cby.frame.common.exception.BusinessAssert;
import per.cby.frame.common.util.BaseUtil;
import per.cby.frame.common.util.IDUtil;
import per.cby.frame.common.util.SpringContextUtil;
import per.cby.frame.mqtt.annotation.MqttConsumer;
import per.cby.frame.mqtt.config.properties.SubscriberProperties;

import lombok.NoArgsConstructor;

/**
 * MQTT消息接收适配器
 * 
 * @author chenboyang
 * @since 2019年6月27日
 *
 */
@NoArgsConstructor
public abstract class AbstractMqttAdapter<T> implements MessageHandler {

    /** 计数器 */
    private static AtomicInteger counter = new AtomicInteger();

    /** 配置属性 */
    private SubscriberProperties properties;

    @Autowired(required = false)
    private ApplicationContext applicationContext;

    public AbstractMqttAdapter(SubscriberProperties properties) {
        this.properties = properties;
    }

    /**
     * 初始化消息接收适配器
     */
    @PostConstruct
    public void init() {
        SpringContextUtil.setContext(applicationContext);
        refreshProperties();
        MqttPahoMessageDrivenChannelAdapter adapter = SpringContextUtil.registerBean(properties.getName(),
                MqttPahoMessageDrivenChannelAdapter.class, properties.getClientId(), properties.getClientFactory(),
                properties.getTopic());
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
        converter.setPayloadAsBytes(true);
        adapter.setConverter(converter);
        DirectChannel outputChannel = new DirectChannel();
        outputChannel.subscribe(this);
        adapter.setOutputChannel(outputChannel);
        if (ArrayUtils.isNotEmpty(properties.getQos())) {
            adapter.setQos(properties.getQos());
        }
    }

    /**
     * MQTT消费者配置信息
     * 
     * @return 配置信息
     */
    protected MqttConsumer mqttConf() {
        return getClass().getAnnotation(MqttConsumer.class);
    }

    /**
     * 刷新配置属性
     * 
     * @return 订阅器属性
     */
    protected SubscriberProperties refreshProperties() {
        if (properties == null) {
            properties = new SubscriberProperties();
        }
        MqttConsumer conf = mqttConf();
        if (conf != null) {
            if (StringUtils.isBlank(properties.getName()) && StringUtils.isNotBlank(conf.name())) {
                properties.setName(conf.name());
            }
            if (StringUtils.isBlank(properties.getClientId()) && StringUtils.isNotBlank(conf.clientId())) {
                properties.setClientId(conf.clientId());
            }
            if (properties.getClientFactory() == null && StringUtils.isNotBlank(conf.clientFactory())) {
                properties.setClientFactory(SpringContextUtil.getBean(conf.clientFactory()));
            }
            if (ArrayUtils.isEmpty(properties.getTopic()) && ArrayUtils.isNotEmpty(conf.topic())) {
                properties.setTopic(conf.topic());
            }
            if (ArrayUtils.isEmpty(properties.getQos()) && ArrayUtils.isNotEmpty(conf.qos())) {
                properties.setQos(conf.qos());
            }
            if (properties.getCharset() == null && StringUtils.isNotBlank(conf.charset())) {
                properties.setCharset(Charset.forName(conf.charset()));
            }
        }
        if (StringUtils.isBlank(properties.getName())) {
            StringBuilder sb = new StringBuilder();
            sb.append(getClass().getSimpleName().substring(0, 1).toLowerCase());
            sb.append(getClass().getSimpleName().substring(1));
            sb.append(AbstractMqttAdapter.class.getSimpleName());
            sb.append(counter.incrementAndGet());
            properties.setName(sb.toString());
        }
        if (StringUtils.isBlank(properties.getClientId())) {
            properties.setClientId(IDUtil.createUUID32());
        } else {
            properties.setClientId(properties.getClientId() + ":" + BaseUtil.getIp());
        }
        if (properties.getClientFactory() == null) {
            properties.setClientFactory(SpringContextUtil.getBean(MqttPahoClientFactory.class));
        }
        BusinessAssert.notNull(properties.getClientFactory(), "MQTT客户端构建工厂为空！");
        BusinessAssert.notEmpty(properties.getTopic(), getClass().getName() + "-MQTT订阅者topic为空！");
        return properties;
    }

    /**
     * 获取字符集编码
     * 
     * @return 字符集编码
     */
    protected Charset charset() {
        return Optional.ofNullable(properties.getCharset()).orElse(Charsets.UTF_8);
    }

    /**
     * 处理消息
     * 
     * @param headers 消息头
     * @param payload 消息载荷
     */
    protected abstract void handleMessage(MessageHeaders headers, T payload);

}
