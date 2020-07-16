package per.cby.frame.redis.factory;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import per.cby.frame.common.util.SpringContextUtil;
import per.cby.frame.redis.handler.RedisHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Redis数据库处理器构建工厂接口实现类
 * 
 * @author chenboyang
 *
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class RedisHandlerFactoryImpl implements RedisHandlerFactory {

    /** 连接工厂 */
    private RedisConnectionFactory connectionFactory;

    /** 字符串序列化器 */
    private StringRedisSerializer stringRedisSerializer;

    /** JSON序列化器 */
    private GenericJackson2JsonRedisSerializer jsonRedisSerializer;

    /**
     * 构建Redis处理器工厂
     */
    public RedisHandlerFactoryImpl() {
        this(SpringContextUtil.getBean(RedisConnectionFactory.class));
    }

    /**
     * 构建Redis处理器工厂
     * 
     * @param connectionFactory 连接工厂
     */
    public RedisHandlerFactoryImpl(RedisConnectionFactory connectionFactory) {
        this(connectionFactory, SpringContextUtil.getBean(StringRedisSerializer.class),
                SpringContextUtil.getBean(GenericJackson2JsonRedisSerializer.class));
    }

    @Override
    public <K, V> RedisHandler<K, V> buildHandler() {
        return buildHandler(connectionFactory);
    }

    @Override
    public <K, V> RedisHandler<K, V> buildHandler(RedisConnectionFactory connectionFactory) {
        RedisHandler<K, V> redisHandler = new RedisHandler<K, V>(connectionFactory);
        redisHandler.setKeySerializer(stringRedisSerializer);
        redisHandler.setValueSerializer(jsonRedisSerializer);
        redisHandler.setHashKeySerializer(stringRedisSerializer);
        redisHandler.setHashValueSerializer(jsonRedisSerializer);
        return redisHandler;
    }

}
