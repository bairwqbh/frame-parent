package per.cby.frame.redis.storage.zset;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.redis.connection.RedisZSetCommands.Limit;
import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import per.cby.frame.redis.storage.RedisContainerStorage;

/**
 * Redis有序集合存储接口
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
public interface RedisZSetStorage<T> extends RedisContainerStorage {

    /**
     * 将当前集合与其它集合的交集存入目标集合
     * 
     * @param otherKey 其它集合键
     * @param destKey  目标集合键
     * @return 存入集合长度
     */
    long intersectAndStore(String otherKey, String destKey);

    /**
     * 将当前集合与其它多个集合的交集存入目标集合
     * 
     * @param otherKeys 其它集合键集
     * @param destKey   目标集合键
     * @return 存入集合长度
     */
    long intersectAndStore(Collection<String> otherKeys, String destKey);

    /**
     * 将当前集合与其它集合的并集存入目标集合
     * 
     * @param otherKey 其它集合键
     * @param destKey  目标集合键
     * @return 存入集合长度
     */
    long unionAndStore(String otherKey, String destKey);

    /**
     * 将当前集合与其它多个集合的并集存入目标集合
     * 
     * @param otherKeys 其它集合键集
     * @param destKey   目标集合键
     * @return 存入集合长度
     */
    long unionAndStore(Collection<String> otherKeys, String destKey);

    /**
     * 获取范围内的元素
     * 
     * @param start 开始下标
     * @param end   结束下标
     * @return 元素集合
     */
    Set<T> range(long start, long end);

    /**
     * 获取范围内的元素以递减排序
     * 
     * @param start 开始下标
     * @param end   结束下标
     * @return 元素集合
     */
    Set<T> reverseRange(long start, long end);

    /**
     * 获取范围内的元组
     * 
     * @param start 开始下标
     * @param end   结束下标
     * @return 元组集合
     */
    Set<TypedTuple<T>> rangeWithScores(long start, long end);

    /**
     * 获取范围内的元组以递减排序
     * 
     * @param start 开始下标
     * @param end   结束下标
     * @return 元组集合
     */
    Set<TypedTuple<T>> reverseRangeWithScores(long start, long end);

    /**
     * 根据字典区间获取元素
     * 
     * @param range 范围
     * @return 元素集合
     */
    Set<T> rangeByLex(Range range);

    /**
     * 根据字典区间和限制获取元素
     * 
     * @param range 范围
     * @param limit 限制
     * @return 元素集合
     */
    Set<T> rangeByLex(Range range, Limit limit);

    /**
     * 根据分数范围获取元素
     * 
     * @param min 最小值
     * @param max 最大值
     * @return 元素集合
     */
    Set<T> rangeByScore(double min, double max);

    /**
     * 根据分数范围偏移量和数量获取元素
     * 
     * @param min    最小值
     * @param max    最大值
     * @param offset 偏移
     * @param count  数量
     * @return 元素集合
     */
    Set<T> rangeByScore(double min, double max, long offset, long count);

    /**
     * 根据分数范围获取元素以递减排序
     * 
     * @param min 最小值
     * @param max 最大值
     * @return 元素集合
     */
    Set<T> reverseRangeByScore(double min, double max);

    /**
     * 根据分数范围偏移量和数量获取元素以递减排序
     * 
     * @param min    最小值
     * @param max    最大值
     * @param offset 偏移
     * @param count  数量
     * @return 元素集合
     */
    Set<T> reverseRangeByScore(double min, double max, long offset, long count);

    /**
     * 根据分数范围获取元组
     * 
     * @param min 最小值
     * @param max 最大值
     * @return 元组集合
     */
    Set<TypedTuple<T>> rangeByScoreWithScores(double min, double max);

    /**
     * 根据分数范围偏移量和数量获取元组
     * 
     * @param min    最小值
     * @param max    最大值
     * @param offset 偏移
     * @param count  数量
     * @return 元组集合
     */
    Set<TypedTuple<T>> rangeByScoreWithScores(double min, double max, long offset, long count);

    /**
     * 根据分数范围获取元组以递减排序
     * 
     * @param min 最小值
     * @param max 最大值
     * @return 元组集合
     */
    Set<TypedTuple<T>> reverseRangeByScoreWithScores(double min, double max);

    /**
     * 根据分数范围偏移量和数量获取元组以递减排序
     * 
     * @param min    最小值
     * @param max    最大值
     * @param offset 偏移
     * @param count  数量
     * @return 元组集合
     */
    Set<TypedTuple<T>> reverseRangeByScoreWithScores(double min, double max, long offset, long count);

    /**
     * 新增元素
     * 
     * @param value 元素
     * @param score 分数
     * @return 新增是否成功
     */
    boolean add(T value, double score);

    /**
     * 新增元组集合
     * 
     * @param tuples 元组集合
     * @return 新增元素数量
     */
    long add(Set<TypedTuple<T>> tuples);

    /**
     * 对元素的分数进行增量更新
     * 
     * @param value 元素
     * @param delta 增量
     * @return 元素最新分数
     */
    double incrementScore(T value, double delta);

    /**
     * 获取元素排名
     * 
     * @param o 元素
     * @return 排名
     */
    long rank(T o);

    /**
     * 获取元素反向排名
     * 
     * @param o 元素
     * @return 反向排名
     */
    long reverseRank(T o);

    /**
     * 获取元素的分数
     * 
     * @param o 元素
     * @return 分数
     */
    double score(T o);

    /**
     * 移除集合中多个元素
     * 
     * @param values 元素集合
     * @return 移除元素数量
     */
    long remove(Collection<? extends T> values);

    /**
     * 移除范围内的元素
     * 
     * @param start 开始下标
     * @param end   结束下标
     * @return 移除元素数量
     */
    long removeRange(long start, long end);

    /**
     * 根据分数范围移除元素
     * 
     * @param min 最小值
     * @param max 最大值
     * @return 移除元素数量
     */
    long removeRangeByScore(double min, double max);

    /**
     * 获取分数范围内元素数量
     * 
     * @param min 最小值
     * @param max 最大值
     * @return 元素数量
     */
    long count(double min, double max);

    /**
     * 获取集合长度
     * 
     * @return 集合长度
     */
    long size();

    /**
     * 获取集合长度
     * 
     * @return 集合长度
     */
    long zCard();

    /**
     * 获取列表存储内所有元素
     * 
     * @return 元素集合
     */
    Set<T> set();

}
