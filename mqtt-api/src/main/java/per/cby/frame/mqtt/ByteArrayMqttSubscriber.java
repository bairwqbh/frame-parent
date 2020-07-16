package per.cby.frame.mqtt;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

import per.cby.frame.mqtt.config.properties.SubscriberProperties;

import lombok.NoArgsConstructor;

/**
 * MQTT字节数组类型消息订阅器
 * 
 * @author chenboyang
 * @since 2019年6月28日
 *
 */
@NoArgsConstructor
public abstract class ByteArrayMqttSubscriber extends AbstractMqttAdapter<byte[]> {

    public ByteArrayMqttSubscriber(SubscriberProperties properties) {
        super(properties);
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        byte[] data = null;
        Object obj = message.getPayload();
        if (obj != null) {
            data = (byte[]) obj;
        }
        handleMessage(message.getHeaders(), data);
    }

}
