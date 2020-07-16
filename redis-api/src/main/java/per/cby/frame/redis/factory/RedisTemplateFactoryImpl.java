package per.cby.frame.redis.factory;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import per.cby.frame.common.util.SpringContextUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Redis数据库操作模板构建工厂接口实现类
 * 
 * @author chenboyang
 *
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class RedisTemplateFactoryImpl implements RedisTemplateFactory {

    /** 连接工厂 */
    private RedisConnectionFactory connectionFactory;

    /** 字符串序列化器 */
    private StringRedisSerializer stringRedisSerializer;

    /** JSON序列化器 */
    private GenericJackson2JsonRedisSerializer jsonRedisSerializer;

    /** 构建Redis处理模板工厂 */
    public RedisTemplateFactoryImpl() {
        this(SpringContextUtil.getBean(RedisConnectionFactory.class));
    }

    /**
     * 构建Redis处理模板工厂
     * 
     * @param connectionFactory 连接工厂
     */
    public RedisTemplateFactoryImpl(RedisConnectionFactory connectionFactory) {
        this(connectionFactory, SpringContextUtil.getBean(StringRedisSerializer.class),
                SpringContextUtil.getBean(GenericJackson2JsonRedisSerializer.class));
    }

    @Override
    public <K, V> RedisTemplate<K, V> buildTemplate() {
        return buildTemplate(connectionFactory);
    }

    @Override
    public <K, V> RedisTemplate<K, V> buildTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<K, V>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        return redisTemplate;
    }

}
