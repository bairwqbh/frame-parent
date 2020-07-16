package per.cby.frame.redis.original.storage.zset;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.redis.connection.RedisZSetCommands.Limit;
import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import per.cby.frame.redis.original.storage.RedisContainerStorage;

/**
 * Redis有序集合存储接口
 * 
 * @author chenboyang
 *
 * @param <E> 元素类型
 */
@Deprecated
public interface RedisZSetStorage<E> extends RedisContainerStorage<String, E> {

    /**
     * 获取列表操作器
     * 
     * @return 哈希操作器
     */
    default ZSetOperations<String, E> ops() {
        return handler().opsForZSet();
    }

    /**
     * 将当前集合与其它集合的交集存入目标集合
     * 
     * @param otherKey 其它集合键
     * @param destKey  目标集合键
     * @return 存入集合长度
     */
    default long intersectAndStore(String otherKey, String destKey) {
        return Optional.ofNullable(ops().intersectAndStore(key(), otherKey, destKey)).orElse(0L);
    }

    /**
     * 将当前集合与其它多个集合的交集存入目标集合
     * 
     * @param otherKeys 其它集合键集
     * @param destKey   目标集合键
     * @return 存入集合长度
     */
    default long intersectAndStore(Collection<String> otherKeys, String destKey) {
        return Optional.ofNullable(ops().intersectAndStore(key(), otherKeys, destKey)).orElse(0L);
    }

    /**
     * 将当前集合与其它集合的并集存入目标集合
     * 
     * @param otherKey 其它集合键
     * @param destKey  目标集合键
     * @return 存入集合长度
     */
    default long unionAndStore(String otherKey, String destKey) {
        return Optional.ofNullable(ops().unionAndStore(key(), otherKey, destKey)).orElse(0L);
    }

    /**
     * 将当前集合与其它多个集合的并集存入目标集合
     * 
     * @param otherKeys 其它集合键集
     * @param destKey   目标集合键
     * @return 存入集合长度
     */
    default long unionAndStore(Collection<String> otherKeys, String destKey) {
        return Optional.ofNullable(ops().unionAndStore(key(), otherKeys, destKey)).orElse(0L);
    }

    /**
     * 获取范围内的元素
     * 
     * @param start 开始下标
     * @param end   结束下标
     * @return 元素集合
     */
    default Set<E> range(long start, long end) {
        return ops().range(key(), start, end);
    }

    /**
     * 获取范围内的元素以递减排序
     * 
     * @param start 开始下标
     * @param end   结束下标
     * @return 元素集合
     */
    default Set<E> reverseRange(long start, long end) {
        return ops().reverseRange(key(), start, end);
    }

    /**
     * 获取范围内的元组
     * 
     * @param start 开始下标
     * @param end   结束下标
     * @return 元组集合
     */
    default Set<TypedTuple<E>> rangeWithScores(long start, long end) {
        return ops().rangeWithScores(key(), start, end);
    }

    /**
     * 获取范围内的元组以递减排序
     * 
     * @param start 开始下标
     * @param end   结束下标
     * @return 元组集合
     */
    default Set<TypedTuple<E>> reverseRangeWithScores(long start, long end) {
        return ops().reverseRangeWithScores(key(), start, end);
    }

    /**
     * 根据字典区间获取元素
     * 
     * @param range 范围
     * @return 元素集合
     */
    default Set<E> rangeByLex(Range range) {
        return ops().rangeByLex(key(), range);
    }

    /**
     * 根据字典区间和限制获取元素
     * 
     * @param range 范围
     * @param limit 限制
     * @return 元素集合
     */
    default Set<E> rangeByLex(Range range, Limit limit) {
        return ops().rangeByLex(key(), range, limit);
    }

    /**
     * 根据分数范围获取元素
     * 
     * @param min 最小值
     * @param max 最大值
     * @return 元素集合
     */
    default Set<E> rangeByScore(double min, double max) {
        return ops().rangeByScore(key(), min, max);
    }

    /**
     * 根据分数范围偏移量和数量获取元素
     * 
     * @param min    最小值
     * @param max    最大值
     * @param offset 偏移
     * @param count  数量
     * @return 元素集合
     */
    default Set<E> rangeByScore(double min, double max, long offset, long count) {
        return ops().rangeByScore(key(), min, max, offset, count);
    }

    /**
     * 根据分数范围获取元素以递减排序
     * 
     * @param min 最小值
     * @param max 最大值
     * @return 元素集合
     */
    default Set<E> reverseRangeByScore(double min, double max) {
        return ops().reverseRangeByScore(key(), min, max);
    }

    /**
     * 根据分数范围偏移量和数量获取元素以递减排序
     * 
     * @param min    最小值
     * @param max    最大值
     * @param offset 偏移
     * @param count  数量
     * @return 元素集合
     */
    default Set<E> reverseRangeByScore(double min, double max, long offset, long count) {
        return ops().reverseRangeByScore(key(), min, max, offset, count);
    }

    /**
     * 根据分数范围获取元组
     * 
     * @param min 最小值
     * @param max 最大值
     * @return 元组集合
     */
    default Set<TypedTuple<E>> rangeByScoreWithScores(double min, double max) {
        return ops().rangeByScoreWithScores(key(), min, max);
    }

    /**
     * 根据分数范围偏移量和数量获取元组
     * 
     * @param min    最小值
     * @param max    最大值
     * @param offset 偏移
     * @param count  数量
     * @return 元组集合
     */
    default Set<TypedTuple<E>> rangeByScoreWithScores(double min, double max, long offset, long count) {
        return ops().rangeByScoreWithScores(key(), min, max, offset, count);
    }

    /**
     * 根据分数范围获取元组以递减排序
     * 
     * @param min 最小值
     * @param max 最大值
     * @return 元组集合
     */
    default Set<TypedTuple<E>> reverseRangeByScoreWithScores(double min, double max) {
        return ops().reverseRangeByScoreWithScores(key(), min, max);
    }

    /**
     * 根据分数范围偏移量和数量获取元组以递减排序
     * 
     * @param min    最小值
     * @param max    最大值
     * @param offset 偏移
     * @param count  数量
     * @return 元组集合
     */
    default Set<TypedTuple<E>> reverseRangeByScoreWithScores(double min, double max, long offset, long count) {
        return ops().reverseRangeByScoreWithScores(key(), min, max, offset, count);
    }

    /**
     * 新增元素
     * 
     * @param value 元素
     * @param score 分数
     * @return 新增是否成功
     */
    default boolean add(E value, double score) {
        return Optional.ofNullable(ops().add(key(), value, score)).orElse(false);
    }

    /**
     * 新增元组集合
     * 
     * @param tuples 元组集合
     * @return 新增元素数量
     */
    default long add(Set<TypedTuple<E>> tuples) {
        return Optional.ofNullable(ops().add(key(), tuples)).orElse(0L);
    }

    /**
     * 对元素的分数进行增量更新
     * 
     * @param value 元素
     * @param delta 增量
     * @return 元素最新分数
     */
    default double incrementScore(E value, double delta) {
        return Optional.ofNullable(ops().incrementScore(key(), value, delta)).orElse(0D);
    }

    /**
     * 获取元素排名
     * 
     * @param o 元素
     * @return 排名
     */
    default long rank(E o) {
        return Optional.ofNullable(ops().rank(key(), o)).orElse(0L);
    }

    /**
     * 获取元素反向排名
     * 
     * @param o 元素
     * @return 反向排名
     */
    default long reverseRank(E o) {
        return Optional.ofNullable(ops().reverseRank(key(), o)).orElse(0L);
    }

    /**
     * 获取元素的分数
     * 
     * @param o 元素
     * @return 分数
     */
    default double score(E o) {
        return Optional.ofNullable(ops().score(key(), o)).orElse(0D);
    }

    /**
     * 移除集合中多个元素
     * 
     * @param values 元素集合
     * @return 移除元素数量
     */
    default long remove(Collection<? extends E> values) {
        return Optional.ofNullable(ops().remove(key(), values.toArray())).orElse(0L);
    }

    /**
     * 移除范围内的元素
     * 
     * @param start 开始下标
     * @param end   结束下标
     * @return 移除元素数量
     */
    default long removeRange(long start, long end) {
        return Optional.ofNullable(ops().removeRange(key(), start, end)).orElse(0L);
    }

    /**
     * 根据分数范围移除元素
     * 
     * @param min 最小值
     * @param max 最大值
     * @return 移除元素数量
     */
    default long removeRangeByScore(double min, double max) {
        return Optional.ofNullable(ops().removeRangeByScore(key(), min, max)).orElse(0L);
    }

    /**
     * 获取分数范围内元素数量
     * 
     * @param min 最小值
     * @param max 最大值
     * @return 元素数量
     */
    default long count(double min, double max) {
        return Optional.ofNullable(ops().count(key(), min, max)).orElse(0L);
    }

    /**
     * 获取集合长度
     * 
     * @return 集合长度
     */
    default long size() {
        return Optional.ofNullable(ops().size(key())).orElse(0L);
    }

    /**
     * 获取集合长度
     * 
     * @return 集合长度
     */
    default long zCard() {
        return Optional.ofNullable(ops().zCard(key())).orElse(0L);
    }

    /**
     * 扫描元素
     * 
     * @param options 扫描配置项
     * @return 元组光标
     */
    default Cursor<TypedTuple<E>> scan(ScanOptions options) {
        return ops().scan(key(), options);
    }

    /**
     * 获取列表存储内所有元素
     * 
     * @return 元素集合
     */
    default Set<E> set() {
        return range(0, -1);
    }

}
