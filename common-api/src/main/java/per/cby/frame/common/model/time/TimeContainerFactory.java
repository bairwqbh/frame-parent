package per.cby.frame.common.model.time;

import java.util.concurrent.TimeUnit;

import lombok.experimental.UtilityClass;

/**
 * 时间容器创建工厂
 * 
 * @author chenboyang
 *
 */
@UtilityClass
public class TimeContainerFactory {

    /**
     * 创建哈希时间容器
     * 
     * @return 时间哈希容器
     */
    public <K, V> TimeHashMap<K, V> createTimeHashMap() {
        return new TimeHashMap<K, V>();
    }

    /**
     * 创建哈希时间容器
     * 
     * @param timeout 时间元素默认过期时间
     * @return 时间哈希容器
     */
    public <K, V> TimeHashMap<K, V> createTimeHashMap(long timeout) {
        return new TimeHashMap<K, V>(timeout);
    }

    /**
     * 创建哈希时间容器
     * 
     * @param interval 监听器执行间隔时间
     * @param timeout  时间元素默认过期时间
     * @return 时间哈希容器
     */
    public <K, V> TimeHashMap<K, V> createTimeHashMap(long interval, long timeout) {
        return new TimeHashMap<K, V>(interval, timeout);
    }

    /**
     * 创建哈希时间容器
     * 
     * @param interval 容器监听执行间隔时间
     * @param timeout  时间元素默认过期时间
     * @param timeUnit 监听器执行时间单位和时间元素默认时间单位
     * @return 时间哈希容器
     */
    public <K, V> TimeHashMap<K, V> createTimeHashMap(long interval, long timeout, TimeUnit timeUnit) {
        return new TimeHashMap<K, V>(interval, timeout, timeUnit);
    }

    /**
     * 创建哈希时间容器
     * 
     * @param delay    容器监听延迟执行时间
     * @param interval 容器监听执行间隔时间
     * @param timeout  时间元素默认过期时间
     * @param timeUnit 监听器执行时间单位和时间元素默认时间单位
     * @return 时间哈希容器
     */
    public <K, V> TimeHashMap<K, V> createTimeHashMap(long delay, long interval, long timeout, TimeUnit timeUnit) {
        return new TimeHashMap<K, V>(delay, interval, timeout, timeUnit);
    }

    /**
     * 创建哈希时间容器
     * 
     * @return 时间列表容器
     */
    public <E> TimeArrayList<E> createTimeArrayList() {
        return new TimeArrayList<E>();
    }

    /**
     * 创建哈希时间容器
     * 
     * @param timeout 时间元素默认过期时间
     * @return 时间列表容器
     */
    public <E> TimeArrayList<E> createTimeArrayList(long timeout) {
        return new TimeArrayList<E>(timeout);
    }

    /**
     * 创建哈希时间容器
     * 
     * @param timeout  时间元素默认过期时间
     * @param timeUnit 监听器执行时间单位和时间元素默认时间单位
     * @return 时间列表容器
     */
    public <E> TimeArrayList<E> createTimeArrayList(long interval, long timeout) {
        return new TimeArrayList<E>(interval, timeout);
    }

    /**
     * 创建哈希时间容器
     * 
     * @param interval 容器监听执行间隔时间
     * @param timeout  时间元素默认过期时间
     * @param timeUnit 监听器执行时间单位和时间元素默认时间单位
     * @return 时间列表容器
     */
    public <E> TimeArrayList<E> createTimeArrayList(long interval, long timeout, TimeUnit timeUnit) {
        return new TimeArrayList<E>(interval, timeout, timeUnit);
    }

    /**
     * 创建哈希时间容器
     * 
     * @param delay    容器监听延迟执行时间
     * @param interval 容器监听执行间隔时间
     * @param timeout  时间元素默认过期时间
     * @param timeUnit 监听器执行时间单位和时间元素默认时间单位
     * @return 时间列表容器
     */
    public <E> TimeArrayList<E> createTimeArrayList(long delay, long interval, long timeout, TimeUnit timeUnit) {
        return new TimeArrayList<E>(delay, interval, timeout, timeUnit);
    }

}
