package per.cby.frame.kafka.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.RecordMessageConverter;

/**
 * kafka配置
 * 
 * @author chenboyang
 *
 */
@Configuration("__KAFKA_CONFIG__")
public class KafkaConfig {

    @Autowired
    private KafkaProperties properties;

    /**
     * 转换器配置
     * 
     * @return 转换器
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty("spring.kafka.properties.converter")
    public RecordMessageConverter converter()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String str = properties.getProperties().get("converter");
        if (StringUtils.isBlank(str)) {
            return null;
        }
        Class<?> clazz = Class.forName(str);
        if (clazz == null || !RecordMessageConverter.class.isAssignableFrom(clazz)) {
            return null;
        }
        return (RecordMessageConverter) clazz.newInstance();
    }

}
