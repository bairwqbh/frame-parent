package per.cby.frame.web.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import per.cby.frame.common.config.properties.SysProperties;
import per.cby.frame.common.useable.UseableAuthenService;
import per.cby.frame.common.util.DateUtil;
import per.cby.frame.web.service.ApplyService;
import per.cby.frame.web.service.ValidateSignService;
import per.cby.frame.web.service.impl.ApplyServiceImpl;
import per.cby.frame.web.service.impl.UseableAuthenServiceImpl;
import per.cby.frame.web.service.impl.ValidateSignServiceImpl;
import per.cby.frame.web.session.BizSessionIdGenerator;
import per.cby.frame.web.session.L2CacheSessionManager;
import per.cby.frame.web.session.RedisSessionCache;
import per.cby.frame.web.session.SessionCache;
import per.cby.frame.web.session.SessionManager;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

/**
 * MVC配置类
 * 
 * @author chenboyang
 *
 */
@ConditionalOnWebApplication
@Configuration("__WEB_APP_CONFIG__")
public class WebAppConfig {

    /**
     * 获取系统应用服务接口
     * 
     * @return 系统应用服务接口
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(name = "javax.servlet.http.HttpServletRequest")
    public ApplyService applyService() {
        return new ApplyServiceImpl();
    }

    /**
     * 获取验证签名服务
     * 
     * @return 验证签名服务
     */
    @Bean
    @ConditionalOnMissingBean(value = ValidateSignService.class, name = "validateSignService")
    @ConditionalOnClass(name = "per.cby.frame.redis.storage.hash.DefaultRedisHashStorage")
    public ValidateSignService validateSignService() {
        return new ValidateSignServiceImpl();
    }

    /**
     * 获取用户认证服务接口实现类
     * 
     * @return 用户认证服务接口实现类
     */
    @Bean
    @ConditionalOnMissingBean
    public UseableAuthenService useableAuthenService() {
        return new UseableAuthenServiceImpl();
    }

    /**
     * 接口返回统一处理切面
     * 
     * @return 接口返回统一处理切面
     */
//    @Bean
//    @ConditionalOnMissingBean
//    @ConditionalOnClass(name = "javax.servlet.http.HttpServletRequest")
//    public ReturnAspect returnAspect() {
//        return new ReturnAspect();
//    }

    /**
     * 会话缓存
     * 
     * @return 会话缓存
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(name = "per.cby.frame.redis.storage.value.DefaultCatalogRedisValueStorage")
    public SessionCache sessionCache() {
        return new RedisSessionCache();
    }

    /**
     * 统一会话管理器
     * 
     * @param sysProperties 系统属性
     * @return 会话管理器
     */
    @Bean
    @ConditionalOnMissingBean
    public SessionManager sessionManager(SysProperties sysProperties, SessionCache sessionCache) {
        L2CacheSessionManager manager = new L2CacheSessionManager();
        manager.setGlobalTimeout(sysProperties.getTimeout(), TimeUnit.MINUTES);
        manager.setSessionCache(sessionCache);
        manager.setSessionIdGenerator(new BizSessionIdGenerator());
        return manager;
    }

    /**
     * 日期类型转换器
     * 
     * @return 转换器
     */
    @Bean
    @ConditionalOnMissingBean(name = "localDateConverter")
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                if (StringUtils.isBlank(source)) {
                    return null;
                }
                String format = DateUtil.getFormat(source);
                if (StringUtils.isNotBlank(format)) {
                    return LocalDate.parse(source, DateTimeFormatter.ofPattern(format));
                }
                return LocalDate.parse(source);
            }
        };
    }

    /**
     * 时间类型转换器
     * 
     * @return 转换器
     */
    @Bean
    @ConditionalOnMissingBean(name = "localTimeConverter")
    public Converter<String, LocalTime> localTimeConverter() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                if (StringUtils.isBlank(source)) {
                    return null;
                }
                String format = DateUtil.getFormat(source);
                if (StringUtils.isNotBlank(format)) {
                    return LocalTime.parse(source, DateTimeFormatter.ofPattern(format));
                }
                return LocalTime.parse(source);
            }
        };
    }

    /**
     * 日期时间类型转换器
     * 
     * @return 转换器
     */
    @Bean
    @ConditionalOnMissingBean(name = "localDateTimeConverter")
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                if (StringUtils.isBlank(source)) {
                    return null;
                }
                String format = DateUtil.getFormat(source);
                if (StringUtils.isNotBlank(format)) {
                    return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(format));
                }
                return LocalDateTime.parse(source);
            }
        };
    }

    /**
     * 日期时间类型转换模型
     * 
     * @return 转换模型
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(name = "com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer")
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern(DateUtil.DATETIME_FORMAT);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(DateUtil.TIME_FORMAT);
        return builder -> builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(datetimeFormatter))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(datetimeFormatter))
                .serializerByType(LocalDate.class, new LocalDateSerializer(dateFormatter))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(dateFormatter))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(timeFormatter))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
    }

}
