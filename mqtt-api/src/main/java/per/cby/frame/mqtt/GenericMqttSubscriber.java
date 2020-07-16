package per.cby.frame.mqtt;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

import per.cby.frame.common.util.JsonUtil;
import per.cby.frame.common.util.ReflectUtil;
import per.cby.frame.mqtt.config.properties.SubscriberProperties;

import lombok.NoArgsConstructor;

/**
 * MQTT泛型类型消息订阅器
 * 
 * @author chenboyang
 * @since 2019年6月28日
 *
 */
@NoArgsConstructor
@SuppressWarnings("unchecked")
public abstract class GenericMqttSubscriber<T> extends AbstractMqttAdapter<T> {

    public GenericMqttSubscriber(SubscriberProperties properties) {
        super(properties);
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        T t = null;
        Object obj = message.getPayload();
        if (obj != null) {
            String json = new String((byte[]) obj, charset());
            t = (T) JsonUtil.toObject(json, ReflectUtil.getParameterizedType(getClass()));
        }
        handleMessage(message.getHeaders(), t);
    }

}
