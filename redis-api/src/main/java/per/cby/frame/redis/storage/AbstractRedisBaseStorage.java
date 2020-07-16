package per.cby.frame.redis.storage;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.collections.RedisCollectionFactoryBean.CollectionType;

import per.cby.frame.common.util.BaseUtil;
import per.cby.frame.common.util.JsonUtil;
import per.cby.frame.common.util.ReflectUtil;
import per.cby.frame.common.util.SpringContextUtil;
import per.cby.frame.redis.annotation.RedisStorage;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Redis存储服务接口抽象实现
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
@SuppressWarnings("unchecked")
public abstract class AbstractRedisBaseStorage<K, V> implements RedisBaseStorage {

    /** Redis存储操作模板 */
    @Setter
    @Autowired(required = false)
    private StringRedisTemplate template;

    /** 键类型信息 */
    @Setter(AccessLevel.MODULE)
    private Class<K> keyClass;

    /** 值类型信息 */
    @Setter(AccessLevel.MODULE)
    private Class<V> valueClass;

    /** 值类型信息 */
    @Getter
    @Setter
    private TypeReference<V> valTypeRef;

    /**
     * 值类型泛型信息
     * 
     * @return 泛型信息
     */
    private TypeReference<V> valTypeRef() {
        return Optional.ofNullable(valTypeRef).orElseGet(() -> valTypeRef = getValTypeRef());
    }

    @Override
    public StringRedisTemplate template() {
        if (template == null) {
            String handler = Optional.ofNullable(redisConf()).map(RedisStorage::handler).orElse(StringUtils.EMPTY);
            if (StringUtils.isNotBlank(handler)) {
                template = SpringContextUtil.getBean(handler);
            } else {
                template = SpringContextUtil.getBean(StringRedisTemplate.class);
            }
        }
        return template;
    }

    @Override
    public boolean hasKey(String key) {
        return template().hasKey(key);
    }

    /**
     * 获取业务配置信息
     * 
     * @return 配置信息
     */
    protected RedisStorage redisConf() {
        return getClass().getAnnotation(RedisStorage.class);
    }

    /**
     * 获取键类型信息
     * 
     * @return 类型信息
     */
    protected Class<K> keyClass() {
        if (keyClass == null) {
            Class<?> clazz = ReflectUtil.getParameterizedType(getClass(), 0);
            if (clazz == null) {
                if (redisConf() != null) {
                    clazz = redisConf().keyClass();
                } else {
                    clazz = String.class;
                }
            }
            keyClass = (Class<K>) clazz;
        }
        return keyClass;
    }

    /**
     * 获取值类型信息
     * 
     * @return 类型信息
     */
    protected Class<V> valueClass() {
        if (valueClass == null) {
            Class<?> clazz = ReflectUtil.getParameterizedType(getClass(), 1);
            if (clazz == null) {
                if (redisConf() != null) {
                    clazz = redisConf().valueClass();
                } else {
                    clazz = Object.class;
                }
            }
            valueClass = (Class<V>) clazz;
        }
        return valueClass;
    }

    /**
     * 序列化对象
     * 
     * @param t 对象
     * @return JSON字符串
     */
    private <T> String serialize(T t) {
        if (t == null) {
            return null;
        }
        if (String.class.equals(t.getClass())) {
            return (String) t;
        }
        return JsonUtil.toJson(t);
    }

    /**
     * 反序列化JSON字符串
     * 
     * @param str JSON字符串
     * @return 对象
     */
    private <T> T deserialize(String str, Class<T> clazz) {
        if (str == null) {
            return null;
        }
        if (String.class.equals(clazz)) {
            return (T) str;
        }
        if (valTypeRef() != null) {
            return (T) JsonUtil.toObject(str, valTypeRef());
        }
        return (T) JsonUtil.toObject(str, clazz);
    }

    /**
     * 序列化键对象
     * 
     * @param k 键对象
     * @return JSON字符串
     */
    protected String keySerialize(K k) {
        return serialize(k);
    }

    /**
     * 序列化值对象
     * 
     * @param v 值对象
     * @return JSON字符串
     */
    protected String valueSerialize(V v) {
        return serialize(v);
    }

    /**
     * 反序列化键JSON字符串
     * 
     * @param str 键JSON字符串
     * @return 键对象
     */
    protected K keyDeserialize(String str) {
        return deserialize(str, keyClass());
    }

    /**
     * 反序列化值JSON字符串
     * 
     * @param str 值JSON字符串
     * @return 值对象
     */
    protected V valueDeserialize(String str) {
        return deserialize(str, valueClass());
    }

    /**
     * 序列化键对象集
     * 
     * @param arr 键对象集
     * @return 键结果集
     */
    protected String[] keySerialize(K... arr) {
        if (arr == null) {
            return null;
        }
        if (String.class.equals(arr.getClass().getComponentType())) {
            return (String[]) arr;
        }
        String[] array = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            array[i] = keySerialize(arr[i]);
        }
        return array;
    }

    /**
     * 序列化值对象集
     * 
     * @param arr 值对象集
     * @return 值结果集
     */
    protected String[] valueSerialize(V... arr) {
        if (arr == null) {
            return null;
        }
        if (String.class.equals(arr.getClass().getComponentType())) {
            return (String[]) arr;
        }
        String[] array = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            array[i] = valueSerialize(arr[i]);
        }
        return array;
    }

    /**
     * 序列化Map
     * 
     * @param map Map容器
     * @return Map
     */
    protected Map<String, String> serialize(Map<? extends K, ? extends V> map) {
        if (MapUtils.isEmpty(map)) {
            return Collections.emptyMap();
        }
        Map<String, String> kv = BaseUtil.newHashMap(map.size());
        map.forEach((k, v) -> kv.put(keySerialize(k), valueSerialize(v)));
        return kv;
    }

    /**
     * 反序列化Map
     * 
     * @param map Map容器
     * @return Map
     */
    protected Map<K, V> deserialize(Map<String, String> map) {
        if (MapUtils.isEmpty(map)) {
            return Collections.emptyMap();
        }
        Map<K, V> kv = BaseUtil.newHashMap();
        map.forEach((k, v) -> kv.put(keyDeserialize(k), valueDeserialize(v)));
        return kv;
    }

    /**
     * 序列化键集合
     * 
     * @param coll           键集合
     * @param collectionType 集合类型
     * @return 键集合
     */
    protected <R extends Collection<String>, I extends Collection<? extends K>> R keySerialize(I coll,
            CollectionType collectionType) {
        if (CollectionUtils.isEmpty(coll)) {
            return (R) emptyColl(collectionType);
        }
        K k = coll.stream().findFirst().orElse(null);
        Class<?> clazz = k != null ? k.getClass() : keyClass();
        if (String.class.equals(clazz)) {
            return (R) coll;
        }
        return (R) toColl(coll.stream().map(this::keySerialize), collectionType);
    }

    /**
     * 序列化值集合
     * 
     * @param coll           值集合
     * @param collectionType 集合类型
     * @return 值集合
     */
    protected <R extends Collection<String>, I extends Collection<? extends V>> R valueSerialize(I coll,
            CollectionType collectionType) {
        if (CollectionUtils.isEmpty(coll)) {
            return (R) emptyColl(collectionType);
        }
        V v = coll.stream().findFirst().orElse(null);
        Class<?> clazz = v != null ? v.getClass() : valueClass();
        if (String.class.equals(clazz)) {
            return (R) coll;
        }
        return (R) toColl(coll.stream().map(this::valueSerialize), collectionType);
    }

    /**
     * 反序列化键集合
     * 
     * @param coll           键集合
     * @param collectionType 集合类型
     * @return 键集合
     */
    protected <R extends Collection<K>, I extends Collection<String>> R keyDeserialize(I coll,
            CollectionType collectionType) {
        if (CollectionUtils.isEmpty(coll)) {
            return (R) emptyColl(collectionType);
        }
        if (String.class.equals(keyClass())) {
            return (R) coll;
        }
        return (R) toColl(coll.stream().map(this::keyDeserialize), collectionType);
    }

    /**
     * 反序列化值集合
     * 
     * @param coll           值集合
     * @param collectionType 集合类型
     * @return 值集合
     */
    protected <R extends Collection<V>, I extends Collection<String>> R valueDeserialize(I coll,
            CollectionType collectionType) {
        if (CollectionUtils.isEmpty(coll)) {
            return (R) emptyColl(collectionType);
        }
        if (String.class.equals(valueClass())) {
            return (R) coll;
        }
        return (R) toColl(coll.stream().map(this::valueDeserialize), collectionType);
    }

    /**
     * 空集合
     * 
     * @param collectionType 集合类型
     * @return 集合
     */
    private Collection<?> emptyColl(CollectionType collectionType) {
        switch (collectionType) {
            case LIST:
                return Collections.emptyList();
            case SET:
                return Collections.emptySet();
            default:
                return null;
        }
    }

    /**
     * 流转集合
     * 
     * @param stream         流
     * @param collectionType 集合类型
     * @return 集合
     */
    private Collection<?> toColl(Stream<?> stream, CollectionType collectionType) {
        switch (collectionType) {
            case LIST:
                return stream.collect(Collectors.toList());
            case SET:
                return stream.collect(Collectors.toSet());
            default:
                return null;
        }
    }

}
