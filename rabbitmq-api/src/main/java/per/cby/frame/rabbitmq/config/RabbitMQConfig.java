package per.cby.frame.rabbitmq.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MQ配置类
 * 
 * @author chenboyang
 *
 */
@Configuration("__RABBIT_MQ_CONFIG__")
public class RabbitMQConfig {

    /**
     * MQ管理操作器
     * 
     * @return 管理操作器
     */
    @Bean
    @ConditionalOnMissingBean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

}
