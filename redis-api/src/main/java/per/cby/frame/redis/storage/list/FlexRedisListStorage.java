package per.cby.frame.redis.storage.list;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import per.cby.frame.redis.storage.RedisContainerStorage;

/**
 * 灵活的Redis列表存储接口
 * 
 * @author chenboyang
 * 
 * @param <T> 业务类型
 */
@SuppressWarnings("unchecked")
public interface FlexRedisListStorage<T> extends RedisContainerStorage {

    /**
     * 获取指定范围内的元素
     * 
     * @param key 容器键
     * @param start 开始下标
     * @param end   结束下标
     * @return 元素列表
     */
    List<T> range(String key, long start, long end);

    /**
     * 根据开始下标和结束下载截取范围
     * 
     * @param key 容器键
     * @param start 开始下标
     * @param end   结束下载
     */
    void trim(String key, long start, long end);

    /**
     * 获取列表长度
     * 
     * @param key 容器键
     * @return 长度
     */
    long size(String key);

    /**
     * 从列表左侧插入元素
     * 
     * @param key 容器键
     * @param value 元素
     * @return 列表长度
     */
    long leftPush(String key, T value);

    /**
     * 从列表左侧插入多个元素
     * 
     * @param key 容器键
     * @param values 多个元素
     * @return 列表长度
     */
    long leftPushAll(String key, T... values);

    /**
     * 从列表左侧插入一组元素
     * 
     * @param key 容器键
     * @param values 元素集合
     * @return 列表长度
     */
    long leftPushAll(String key, Collection<? extends T> values);

    /**
     * 当容器存在时，从列表左侧插入元素
     * 
     * @param key 容器键
     * @param value 元素
     * @return 列表长度
     */
    long leftPushIfPresent(String key, T value);

    /**
     * 当列表左侧元素为参考值，从列表左侧插入元素
     * 
     * @param key 容器键
     * @param pivot 参考值
     * @param value 元素
     * @return 列表长度
     */
    long leftPush(String key, T pivot, T value);

    /**
     * 从列表右侧插入元素
     * 
     * @param key 容器键
     * @param value 元素
     * @return 列表长度
     */
    long rightPush(String key, T value);

    /**
     * 从列表右侧插入多个元素
     * 
     * @param key 容器键
     * @param values 多个元素
     * @return 列表长度
     */
    long rightPushAll(String key, T... values);

    /**
     * 从列表右侧插入一组元素
     * 
     * @param key 容器键
     * @param values 元素集合
     * @return 列表长度
     */
    long rightPushAll(String key, Collection<? extends T> values);

    /**
     * 当容器存在时，从列表右侧插入元素
     * 
     * @param key 容器键
     * @param value 元素
     * @return 列表长度
     */
    long rightPushIfPresent(String key, T value);

    /**
     * 当列表右侧元素为参考值，从列表右侧插入元素
     * 
     * @param key 容器键
     * @param pivot 参考值
     * @param value 元素
     * @return 列表长度
     */
    long rightPush(String key, T pivot, T value);

    /**
     * 在列表下标位置放置一个元素
     * 
     * @param key 容器键
     * @param index 下标
     * @param value 元素
     */
    void set(String key, long index, T value);

    /**
     * 在列表中删除一个元素
     * 
     * @param key 容器键
     * @param count 计数(count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。<br/>
     *              count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。<br/>
     *              count = 0 : 移除表中所有与 VALUE 相等的值。)
     * @param value 元素
     * @return 列表长度
     */
    long remove(String key, long count, T value);

    /**
     * 获取列表下标位置的元素
     * 
     * @param key 容器键
     * @param index 下标
     * @return 元素
     */
    T index(String key, long index);

    /**
     * 从列表左侧抽出一个元素
     * 
     * @param key 容器键
     * @return 元素
     */
    T leftPop(String key);

    /**
     * 从列表左侧抽出一个元素，当连接中断根据过期时间退出
     * 
     * @param key 容器键
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return 元素
     */
    T leftPop(String key, long timeout, TimeUnit unit);

    /**
     * 从列表右侧抽出一个元素
     * 
     * @param key 容器键
     * @return 元素
     */
    T rightPop(String key);

    /**
     * 从列表右侧抽出一个元素，当连接中断根据过期时间退出
     * 
     * @param key 容器键
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return 元素
     */
    T rightPop(String key, long timeout, TimeUnit unit);

    /**
     * 将当前容器右侧元素移除并插入目标容器的左侧
     * 
     * @param key 容器键
     * @param destinationKey 目标容器键
     * @return 操作的元素
     */
    T rightPopAndLeftPush(String key, String destinationKey);

    /**
     * 将当前容器右侧元素移除并插入目标容器的左侧，当连接中断根据过期时间退出
     * 
     * @param key 容器键
     * @param destinationKey 目标容器键
     * @param timeout        过期时间
     * @param unit           时间单位
     * @return 操作的元素
     */
    T rightPopAndLeftPush(String key, String destinationKey, long timeout, TimeUnit unit);

    /**
     * 获取列表存储内所有元素
     * 
     * @param key 容器键
     * @return 元素列表
     */
    List<T> list(String key);

}
