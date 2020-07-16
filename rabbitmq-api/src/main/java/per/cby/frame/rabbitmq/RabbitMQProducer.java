package per.cby.frame.rabbitmq;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import per.cby.frame.common.util.SpringContextUtil;
import per.cby.frame.rabbitmq.annotation.MQProducer;

/**
 * RabbitMQ消息生产接口
 * 
 * @author chenboyang
 *
 */
public interface RabbitMQProducer {

    /**
     * 获取MQ发送模板
     * 
     * @return 模板
     */
    default RabbitTemplate template() {
        if (StringUtils.isNotBlank(channelInfo().rabbitTemplate())) {
            return SpringContextUtil.getBean(channelInfo().rabbitTemplate());
        } else {
            return SpringContextUtil.getBean(RabbitTemplate.class);
        }
    }

    /**
     * 获取通道信息
     * 
     * @return 信息
     */
    default MQProducer channelInfo() {
        return getClass().getAnnotation(MQProducer.class);
    }

    /**
     * 发送消息
     * 
     * @param obj 消息体
     */
    default void send(Object message) {
        send(channelInfo().exchange(), channelInfo().routingKey(), message);
    }

    /**
     * 发送消息
     * 
     * @param key 业务key值
     * @param obj 消息体
     */
    default void send(String key, Object message) {
        if (StringUtils.isNotBlank(channelInfo().routingKey())) {
            if (channelInfo().routingKey().contains("#")) {
                key = channelInfo().routingKey().replace("#", key);
            } else if (channelInfo().routingKey().contains("*")) {
                key = channelInfo().routingKey().replace("*", key);
            }
        }
        send(channelInfo().exchange(), key, message);
    }

    /**
     * 发送消息
     * 
     * @param exchange   交换机
     * @param routingKey 路由键
     * @param message    消息
     */
    default void send(String exchange, String routingKey, Object message) {
//        String msg = message instanceof String ? (String) message : JsonUtil.toJson(message);
        template().convertAndSend(exchange, routingKey, message);
    }

}
