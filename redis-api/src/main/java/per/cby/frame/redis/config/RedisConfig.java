package per.cby.frame.redis.config;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import per.cby.frame.common.util.DateUtil;
import per.cby.frame.redis.config.properties.RedisSupportProperties;
import per.cby.frame.redis.constant.RedisConstant;
import per.cby.frame.redis.handler.RedisHandler;
import per.cby.frame.redis.storage.hash.DefaultRedisHashStorage;
import per.cby.frame.redis.storage.hash.RedisHashStorage;
import per.cby.frame.redis.task.TaskJobRedisLock;
import per.cby.frame.task.scheduler.DistributeLock;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

/**
 * Redis配置类
 * 
 * @author chenboyang
 *
 */
@Configuration("__REDIS_CONFIG__")
public class RedisConfig {

    /**
     * 连接属性配置
     * 
     * @return 属性配置
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean
    @ConfigurationProperties("spring.redis")
    public RedisSupportProperties redisSupportProperties() {
        return new RedisSupportProperties();
    }

    /**
     * Redis字符串序列化器
     * 
     * @return 序列化器
     */
    @Bean
    @ConditionalOnMissingBean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    /**
     * Redis-JSON序列化器
     * 
     * @return 序列化器
     */
    @Bean
    @ConditionalOnMissingBean
    public GenericJackson2JsonRedisSerializer jsonRedisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.USE_GETTERS_AS_SETTERS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
        objectMapper.setDateFormat(new SimpleDateFormat(DateUtil.DATETIME_FORMAT));
        objectMapper.setTimeZone(TimeZone.getTimeZone(DateUtil.GMT_E_8));
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateUtil.DATETIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateUtil.DATETIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern(DateUtil.TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DateUtil.TIME_FORMAT)));
        objectMapper.registerModule(javaTimeModule);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    /**
     * Redis字符串操作模板
     * 
     * @param connectionFactory 连接工厂
     * @return 操作模板
     */
    @Bean
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(connectionFactory);
        stringRedisTemplate.setEnableTransactionSupport(redisSupportProperties().isEnableTransactionSupport());
        stringRedisTemplate.afterPropertiesSet();
        return stringRedisTemplate;
    }

    /**
     * Redis普通操作模板
     * 
     * @param connectionFactory 连接工厂
     * @return 操作模板
     */
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public <K, V> RedisTemplate<K, V> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<K, V>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(jsonRedisSerializer());
        redisTemplate.setHashKeySerializer(stringRedisSerializer());
        redisTemplate.setHashValueSerializer(jsonRedisSerializer());
        redisTemplate.setEnableTransactionSupport(redisSupportProperties().isEnableTransactionSupport());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * Redis操作处理器
     * 
     * @param connectionFactory 连接工厂
     * @return 操作处理器
     */
    @Bean
    @ConditionalOnMissingBean
    public <K, V> RedisHandler<K, V> redisHandler(RedisConnectionFactory connectionFactory) {
        RedisHandler<K, V> redisHandler = new RedisHandler<K, V>();
        redisHandler.setConnectionFactory(connectionFactory);
        redisHandler.setKeySerializer(stringRedisSerializer());
        redisHandler.setValueSerializer(jsonRedisSerializer());
        redisHandler.setHashKeySerializer(stringRedisSerializer());
        redisHandler.setHashValueSerializer(jsonRedisSerializer());
        redisHandler.setEnableTransactionSupport(redisSupportProperties().isEnableTransactionSupport());
        redisHandler.afterPropertiesSet();
        return redisHandler;
    }

    /**
     * 获取分布式锁接口
     * 
     * @return 分布式锁接口
     */
    @Bean
    @ConditionalOnMissingBean
    public DistributeLock distributeLock() {
        return new TaskJobRedisLock();
    }

    /**
     * 获取请求编号及密钥哈希存储
     * 
     * @return 请求编号及密钥哈希存储
     */
    @Bean(RedisConstant.KEY_SECRET_HASH)
    @ConditionalOnMissingBean(name = RedisConstant.KEY_SECRET_HASH)
    public RedisHashStorage<String, String> keySecretHash() {
        return new DefaultRedisHashStorage<String, String>(RedisConstant.DEFAULT_KEY_SECRET_HASH, String.class,
                String.class);
    }

}
