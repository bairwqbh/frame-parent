package per.cby.frame.rabbitmq.exchange;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import per.cby.frame.rabbitmq.RabbitMQExchangeNames;
import per.cby.frame.rabbitmq.RabbitMQProducer;
import per.cby.frame.rabbitmq.annotation.MQProducer;

/**
 * MQ主题交换机
 * 
 * @author chenboyang
 *
 */
@Lazy
@Component("__TOPIC_EXCHANGE__")
@MQProducer(exchange = RabbitMQExchangeNames.TOPIC)
public class TopicExchange implements RabbitMQProducer {

}
