package per.cby.frame.redis.original.storage.list;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.ListOperations;

import per.cby.frame.redis.original.storage.RedisContainerStorage;

/**
 * Redis列表存储接口
 * 
 * @author chenboyang
 * 
 * @param <E> 元素类型
 */
@Deprecated
@SuppressWarnings("unchecked")
public interface RedisListStorage<E> extends RedisContainerStorage<String, E> {

    /**
     * 获取列表操作器
     * 
     * @return 哈希操作器
     */
    default ListOperations<String, E> ops() {
        return handler().opsForList();
    }

    /**
     * 获取指定范围内的元素
     * 
     * @param start 开始下标
     * @param end   结束下标
     * @return 元素列表
     */
    default List<E> range(long start, long end) {
        return ops().range(key(), start, end);
    }

    /**
     * 根据开始下标和结束下载截取范围
     * 
     * @param start 开始下标
     * @param end   结束下载
     */
    default void trim(long start, long end) {
        ops().trim(key(), start, end);
    }

    /**
     * 获取列表长度
     * 
     * @return 长度
     */
    default long size() {
        return Optional.ofNullable(ops().size(key())).orElse(0L);
    }

    /**
     * 从列表左侧插入元素
     * 
     * @param value 元素
     * @return 列表长度
     */
    default long leftPush(E value) {
        return Optional.ofNullable(ops().leftPush(key(), value)).orElse(0L);
    }

    /**
     * 从列表左侧插入多个元素
     * 
     * @param values 多个元素
     * @return 列表长度
     */
    default long leftPushAll(E... values) {
        return Optional.ofNullable(ops().leftPushAll(key(), values)).orElse(0L);
    }

    /**
     * 从列表左侧插入一组元素
     * 
     * @param values 元素集合
     * @return 列表长度
     */
    default long leftPushAll(Collection<? extends E> values) {
        return Optional.ofNullable(ops().leftPushAll(key(), (Collection<E>) values)).orElse(0L);
    }

    /**
     * 当容器存在时，从列表左侧插入元素
     * 
     * @param value 元素
     * @return 列表长度
     */
    default long leftPushIfPresent(E value) {
        return Optional.ofNullable(ops().leftPushIfPresent(key(), value)).orElse(0L);
    }

    /**
     * 当列表左侧元素为参考值，从列表左侧插入元素
     * 
     * @param pivot 参考值
     * @param value 元素
     * @return 列表长度
     */
    default long leftPush(E pivot, E value) {
        return Optional.ofNullable(ops().leftPush(key(), pivot, value)).orElse(0L);
    }

    /**
     * 从列表右侧插入元素
     * 
     * @param value 元素
     * @return 列表长度
     */
    default long rightPush(E value) {
        return Optional.ofNullable(ops().rightPush(key(), value)).orElse(0L);
    }

    /**
     * 从列表右侧插入多个元素
     * 
     * @param values 多个元素
     * @return 列表长度
     */
    default long rightPushAll(E... values) {
        return Optional.ofNullable(ops().rightPushAll(key(), values)).orElse(0L);
    }

    /**
     * 从列表右侧插入一组元素
     * 
     * @param values 元素集合
     * @return 列表长度
     */
    default long rightPushAll(Collection<? extends E> values) {
        return Optional.ofNullable(ops().rightPushAll(key(), (Collection<E>) values)).orElse(0L);
    }

    /**
     * 当容器存在时，从列表右侧插入元素
     * 
     * @param value 元素
     * @return 列表长度
     */
    default long rightPushIfPresent(E value) {
        return Optional.ofNullable(ops().rightPushIfPresent(key(), value)).orElse(0L);
    }

    /**
     * 当列表右侧元素为参考值，从列表右侧插入元素
     * 
     * @param pivot 参考值
     * @param value 元素
     * @return 列表长度
     */
    default long rightPush(E pivot, E value) {
        return Optional.ofNullable(ops().rightPush(key(), pivot, value)).orElse(0L);
    }

    /**
     * 在列表下标位置放置一个元素
     * 
     * @param index 下标
     * @param value 元素
     */
    default void set(long index, E value) {
        ops().set(key(), index, value);
    }

    /**
     * 在列表中删除一个元素
     * 
     * @param count 计数(count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。<br/>
     *              count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。<br/>
     *              count = 0 : 移除表中所有与 VALUE 相等的值。)
     * @param value 元素
     * @return 列表长度
     */
    default long remove(long count, E value) {
        return Optional.ofNullable(ops().remove(key(), count, value)).orElse(0L);
    }

    /**
     * 获取列表下标位置的元素
     * 
     * @param index 下标
     * @return 元素
     */
    default E index(long index) {
        return ops().index(key(), index);
    }

    /**
     * 从列表左侧抽出一个元素
     * 
     * @return 元素
     */
    default E leftPop() {
        return ops().leftPop(key());
    }

    /**
     * 从列表左侧抽出一个元素，当连接中断根据过期时间退出
     * 
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return 元素
     */
    default E leftPop(long timeout, TimeUnit unit) {
        return ops().leftPop(key(), timeout, unit);
    }

    /**
     * 从列表右侧抽出一个元素
     * 
     * @return 元素
     */
    default E rightPop() {
        return ops().rightPop(key());
    }

    /**
     * 从列表右侧抽出一个元素，当连接中断根据过期时间退出
     * 
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return 元素
     */
    default E rightPop(long timeout, TimeUnit unit) {
        return ops().rightPop(key(), timeout, unit);
    }

    /**
     * 将当前容器右侧元素移除并插入目标容器的左侧
     * 
     * @param destinationKey 目标容器键
     * @return 操作的元素
     */
    default E rightPopAndLeftPush(String destinationKey) {
        return ops().rightPopAndLeftPush(key(), destinationKey);
    }

    /**
     * 将当前容器右侧元素移除并插入目标容器的左侧，当连接中断根据过期时间退出
     * 
     * @param destinationKey 目标容器键
     * @param timeout        过期时间
     * @param unit           时间单位
     * @return 操作的元素
     */
    default E rightPopAndLeftPush(String destinationKey, long timeout, TimeUnit unit) {
        return ops().rightPopAndLeftPush(key(), destinationKey, timeout, unit);
    }

    /**
     * 获取列表存储内所有元素
     * 
     * @return 元素列表
     */
    default List<E> list() {
        return range(0, -1);
    }

}
