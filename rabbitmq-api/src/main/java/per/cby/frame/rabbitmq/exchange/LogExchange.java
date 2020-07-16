package per.cby.frame.rabbitmq.exchange;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import per.cby.frame.rabbitmq.RabbitMQExchangeNames;
import per.cby.frame.rabbitmq.RabbitMQProducer;
import per.cby.frame.rabbitmq.annotation.MQProducer;

/**
 * 日志交换机
 * 
 * @author chenboyang
 *
 */
@Lazy
@Component("__LOG_EXCHANGE__")
@MQProducer(exchange = RabbitMQExchangeNames.LOG)
public class LogExchange implements RabbitMQProducer {

}
