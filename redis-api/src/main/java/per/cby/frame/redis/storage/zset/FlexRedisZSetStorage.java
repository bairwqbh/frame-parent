package per.cby.frame.redis.storage.zset;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.redis.connection.RedisZSetCommands.Limit;
import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import per.cby.frame.redis.storage.RedisContainerStorage;

/**
 * 灵活的Redis有序集合存储接口
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
public interface FlexRedisZSetStorage<T> extends RedisContainerStorage {

    /**
     * 将当前集合与其它集合的交集存入目标集合
     * 
     * @param key      容器键
     * @param otherKey 其它集合键
     * @param destKey  目标集合键
     * @return 存入集合长度
     */
    long intersectAndStore(String key, String otherKey, String destKey);

    /**
     * 将当前集合与其它多个集合的交集存入目标集合
     * 
     * @param key       容器键
     * @param otherKeys 其它集合键集
     * @param destKey   目标集合键
     * @return 存入集合长度
     */
    long intersectAndStore(String key, Collection<String> otherKeys, String destKey);

    /**
     * 将当前集合与其它集合的并集存入目标集合
     * 
     * @param key      容器键
     * @param otherKey 其它集合键
     * @param destKey  目标集合键
     * @return 存入集合长度
     */
    long unionAndStore(String key, String otherKey, String destKey);

    /**
     * 将当前集合与其它多个集合的并集存入目标集合
     * 
     * @param key       容器键
     * @param otherKeys 其它集合键集
     * @param destKey   目标集合键
     * @return 存入集合长度
     */
    long unionAndStore(String key, Collection<String> otherKeys, String destKey);

    /**
     * 获取范围内的元素
     * 
     * @param key   容器键
     * @param start 开始下标
     * @param end   结束下标
     * @return 元素集合
     */
    Set<T> range(String key, long start, long end);

    /**
     * 获取范围内的元素以递减排序
     * 
     * @param key   容器键
     * @param start 开始下标
     * @param end   结束下标
     * @return 元素集合
     */
    Set<T> reverseRange(String key, long start, long end);

    /**
     * 获取范围内的元组
     * 
     * @param key   容器键
     * @param start 开始下标
     * @param end   结束下标
     * @return 元组集合
     */
    Set<TypedTuple<T>> rangeWithScores(String key, long start, long end);

    /**
     * 获取范围内的元组以递减排序
     * 
     * @param key   容器键
     * @param start 开始下标
     * @param end   结束下标
     * @return 元组集合
     */
    Set<TypedTuple<T>> reverseRangeWithScores(String key, long start, long end);

    /**
     * 根据字典区间获取元素
     * 
     * @param key   容器键
     * @param range 范围
     * @return 元素集合
     */
    Set<T> rangeByLex(String key, Range range);

    /**
     * 根据字典区间和限制获取元素
     * 
     * @param key   容器键
     * @param range 范围
     * @param limit 限制
     * @return 元素集合
     */
    Set<T> rangeByLex(String key, Range range, Limit limit);

    /**
     * 根据分数范围获取元素
     * 
     * @param key 容器键
     * @param min 最小值
     * @param max 最大值
     * @return 元素集合
     */
    Set<T> rangeByScore(String key, double min, double max);

    /**
     * 根据分数范围偏移量和数量获取元素
     * 
     * @param key    容器键
     * @param min    最小值
     * @param max    最大值
     * @param offset 偏移
     * @param count  数量
     * @return 元素集合
     */
    Set<T> rangeByScore(String key, double min, double max, long offset, long count);

    /**
     * 根据分数范围获取元素以递减排序
     * 
     * @param key 容器键
     * @param min 最小值
     * @param max 最大值
     * @return 元素集合
     */
    Set<T> reverseRangeByScore(String key, double min, double max);

    /**
     * 根据分数范围偏移量和数量获取元素以递减排序
     * 
     * @param key    容器键
     * @param min    最小值
     * @param max    最大值
     * @param offset 偏移
     * @param count  数量
     * @return 元素集合
     */
    Set<T> reverseRangeByScore(String key, double min, double max, long offset, long count);

    /**
     * 根据分数范围获取元组
     * 
     * @param key 容器键
     * @param min 最小值
     * @param max 最大值
     * @return 元组集合
     */
    Set<TypedTuple<T>> rangeByScoreWithScores(String key, double min, double max);

    /**
     * 根据分数范围偏移量和数量获取元组
     * 
     * @param key    容器键
     * @param min    最小值
     * @param max    最大值
     * @param offset 偏移
     * @param count  数量
     * @return 元组集合
     */
    Set<TypedTuple<T>> rangeByScoreWithScores(String key, double min, double max, long offset, long count);

    /**
     * 根据分数范围获取元组以递减排序
     * 
     * @param key 容器键
     * @param min 最小值
     * @param max 最大值
     * @return 元组集合
     */
    Set<TypedTuple<T>> reverseRangeByScoreWithScores(String key, double min, double max);

    /**
     * 根据分数范围偏移量和数量获取元组以递减排序
     * 
     * @param key    容器键
     * @param min    最小值
     * @param max    最大值
     * @param offset 偏移
     * @param count  数量
     * @return 元组集合
     */
    Set<TypedTuple<T>> reverseRangeByScoreWithScores(String key, double min, double max, long offset, long count);

    /**
     * 新增元素
     * 
     * @param key   容器键
     * @param value 元素
     * @param score 分数
     * @return 新增是否成功
     */
    boolean add(String key, T value, double score);

    /**
     * 新增元组集合
     * 
     * @param key    容器键
     * @param tuples 元组集合
     * @return 新增元素数量
     */
    long add(String key, Set<TypedTuple<T>> tuples);

    /**
     * 对元素的分数进行增量更新
     * 
     * @param key   容器键
     * @param value 元素
     * @param delta 增量
     * @return 元素最新分数
     */
    double incrementScore(String key, T value, double delta);

    /**
     * 获取元素排名
     * 
     * @param key 容器键
     * @param o   元素
     * @return 排名
     */
    long rank(String key, T o);

    /**
     * 获取元素反向排名
     * 
     * @param key 容器键
     * @param o   元素
     * @return 反向排名
     */
    long reverseRank(String key, T o);

    /**
     * 获取元素的分数
     * 
     * @param key 容器键
     * @param o   元素
     * @return 分数
     */
    double score(String key, T o);

    /**
     * 移除集合中多个元素
     * 
     * @param key    容器键
     * @param values 元素集合
     * @return 移除元素数量
     */
    long remove(String key, Collection<? extends T> values);

    /**
     * 移除范围内的元素
     * 
     * @param key   容器键
     * @param start 开始下标
     * @param end   结束下标
     * @return 移除元素数量
     */
    long removeRange(String key, long start, long end);

    /**
     * 根据分数范围移除元素
     * 
     * @param key 容器键
     * @param min 最小值
     * @param max 最大值
     * @return 移除元素数量
     */
    long removeRangeByScore(String key, double min, double max);

    /**
     * 获取分数范围内元素数量
     * 
     * @param key 容器键
     * @param min 最小值
     * @param max 最大值
     * @return 元素数量
     */
    long count(String key, double min, double max);

    /**
     * 获取集合长度
     * 
     * @param key 容器键
     * @return 集合长度
     */
    long size(String key);

    /**
     * 获取集合长度
     * 
     * @param key 容器键
     * @return 集合长度
     */
    long zCard(String key);

    /**
     * 获取列表存储内所有元素
     * 
     * @param key 容器键
     * @return 元素集合
     */
    Set<T> set(String key);

}
