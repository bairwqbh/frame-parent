package per.cby.frame.common.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * JSON帮助类
 * 
 * @author chenboyang
 *
 */
@Slf4j
@UtilityClass
@SuppressWarnings("unchecked")
public class JsonUtil {

    /** 对象映射器 */
    private volatile ObjectMapper objectMapper = null;

    /**
     * 获取对象映射器
     * 
     * @return 对象映射器
     */
    private ObjectMapper objectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(MapperFeature.USE_GETTERS_AS_SETTERS, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            /* objectMapper.setSerializationInclusion(Include.NON_NULL); */
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
        }
        return objectMapper;
    }

    /**
     * 对象转json字符串
     * 
     * @param obj 对象
     * @return json字符串
     */
    public String toJson(Object obj) {
        try {
            if (obj instanceof String) {
                return String.valueOf(obj);
            }
            return objectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * json字符串转对象
     * 
     * @param json  json字符串
     * @param clazz 对象类型
     * @return 对象
     */
    public <T> T toObject(String json, Class<T> clazz) {
        try {
            if (String.class.equals(clazz)) {
                return (T) json;
            }
            return objectMapper().readValue(json, clazz);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * json字符串转对象
     * 
     * @param json    json字符串
     * @param typeRef 对象类型
     * @return 对象
     */
    public <T> T toObject(String json, TypeReference<T> typeRef) {
        try {
            return objectMapper().readValue(json, typeRef);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 转换对象类型
     * 
     * @param obj   对象
     * @param clazz 对象类型
     * @return 对象
     */
    public <T> T convert(Object obj, Class<T> clazz) {
        return toObject(toJson(obj), clazz);
    }

    /**
     * 转换对象类型
     * 
     * @param obj     对象
     * @param typeRef 对象类型
     * @return 对象
     */
    public <T> T convert(Object obj, TypeReference<T> typeRef) {
        return toObject(toJson(obj), typeRef);
    }

}
