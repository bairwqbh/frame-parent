package per.cby.frame.common.model.time;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;

/**
 * 时间MAP容器接口
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public interface TimeMap<K, V> extends Map<K, V> {

    /**
     * 获取监听器延迟执行时间
     * 
     * @return 延迟时间
     */
    long delay();

    /**
     * 获取监听器执行间隔时间
     * 
     * @return 间隔时间
     */
    long interval();

    /**
     * 获取时间元素默认过期时间
     * 
     * @return 过期时间
     */
    long timeout();

    /**
     * 获取监听器执行时间单位和时间元素默认时间单位
     * 
     * @return 时间单位
     */
    TimeUnit timeUnit();

    /**
     * 获取时间元素容器
     * 
     * @return 时间元素容器
     */
    Map<K, TimeElement<V>> container();

    /**
     * 设置键值和过期时间
     * 
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @return 值
     */
    default V put(K key, V value, long timeout) {
        return put(key, value, timeout, timeUnit());
    }

    /**
     * 设置键值和过期时间
     * 
     * @param key      键
     * @param value    值
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @return 值
     */
    default V put(K key, V value, long timeout, TimeUnit timeUnit) {
        return Optional.ofNullable(container().put(key, new TimeElement<V>(value, timeout, timeUnit)))
                .map(TimeElement::getElement).orElse(value);
    }

    @Override
    default int size() {
        return container().size();
    }

    @Override
    default boolean isEmpty() {
        return container().isEmpty();
    }

    @Override
    default boolean containsKey(Object key) {
        return container().containsKey(key);
    }

    @Override
    default boolean containsValue(Object value) {
        if (value != null && MapUtils.isNotEmpty(container())) {
            for (TimeElement<V> timeElement : container().values()) {
                if (value.equals(timeElement.getElement())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    default V get(Object key) {
        return Optional.ofNullable(container().get(key)).map(TimeElement::getElement).orElse(null);
    }

    @Override
    default V put(K key, V value) {
        return put(key, value, timeout());
    }

    @Override
    default V remove(Object key) {
        return Optional.ofNullable(container().remove(key)).map(TimeElement::getElement).orElse(null);
    }

    @Override
    default void putAll(Map<? extends K, ? extends V> m) {
        if (MapUtils.isNotEmpty(m)) {
            for (K key : m.keySet()) {
                put(key, m.get(key));
            }
        }
    }

    @Override
    default void clear() {
        container().clear();
    }

    @Override
    default Set<K> keySet() {
        return container().keySet();
    }

    @Override
    default Collection<V> values() {
        return container().values().stream().map(TimeElement::getElement).collect(Collectors.toList());
    }

    @Override
    default Set<Entry<K, V>> entrySet() {
        return container().entrySet().stream().map(new Function<Entry<K, TimeElement<V>>, Node<K, V>>() {
            @Override
            public Node<K, V> apply(java.util.Map.Entry<K, TimeElement<V>> entry) {
                return new Node(entry.getKey(), entry.getValue().getElement());
            }
        }).collect(Collectors.toSet());
    }

    /**
     * 存储容器节点类
     * 
     * @author chenboyang
     *
     * @param <K> 键类型
     * @param <V> 值类型
     */
    static class Node<K, V> implements Map.Entry<K, V> {

        /** 键 */
        private final K key;

        /** 值 */
        private V value;

        /**
         * 容器节点构建方法
         * 
         * @param key   键
         * @param value 值
         */
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

    }

}
