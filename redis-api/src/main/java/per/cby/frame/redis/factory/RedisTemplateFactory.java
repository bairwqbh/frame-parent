package per.cby.frame.redis.factory;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis数据库操作模板构建工厂接口
 * 
 * @author chenboyang
 *
 */
public interface RedisTemplateFactory {

    /**
     * 构建Redis处理模板
     * 
     * @return 处理模板
     */
    <K, V> RedisTemplate<K, V> buildTemplate();

    /**
     * 构建Redis处理模板
     * 
     * @param connectionFactory Redis连接工厂
     * @return 处理模板
     */
    <K, V> RedisTemplate<K, V> buildTemplate(RedisConnectionFactory connectionFactory);

}
