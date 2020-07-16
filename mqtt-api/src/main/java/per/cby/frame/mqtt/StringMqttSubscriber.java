package per.cby.frame.mqtt;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

import per.cby.frame.mqtt.config.properties.SubscriberProperties;

import lombok.NoArgsConstructor;

/**
 * MQTT字符串类型消息订阅器
 * 
 * @author chenboyang
 * @since 2019年6月28日
 *
 */
@NoArgsConstructor
public abstract class StringMqttSubscriber extends AbstractMqttAdapter<String> {

    public StringMqttSubscriber(SubscriberProperties properties) {
        super(properties);
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String data = null;
        Object obj = message.getPayload();
        if (obj != null) {
            data = new String((byte[]) obj, charset());
        }
        handleMessage(message.getHeaders(), data);
    }

}
