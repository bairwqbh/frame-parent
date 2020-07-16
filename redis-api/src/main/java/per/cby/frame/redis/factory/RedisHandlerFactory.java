package per.cby.frame.redis.factory;

import org.springframework.data.redis.connection.RedisConnectionFactory;

import per.cby.frame.redis.handler.RedisHandler;

/**
 * Redis数据库处理器构建工厂接口
 * 
 * @author chenboyang
 *
 */
public interface RedisHandlerFactory {

    /**
     * 构建Redis处理器
     * 
     * @return 处理器
     */
    <K, V> RedisHandler<K, V> buildHandler();

    /**
     * 构建Redis处理器
     * 
     * @param connectionFactory Redis连接工厂
     * @return 处理器
     */
    <K, V> RedisHandler<K, V> buildHandler(RedisConnectionFactory connectionFactory);

}
