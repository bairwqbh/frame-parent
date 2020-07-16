package per.cby.frame.redis.storage.zset;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.redis.connection.RedisZSetCommands.Limit;
import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

/**
 * Redis有序集合存储接口
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
public class FlexRedisZSetStorageImpl<T> extends AbstractRedisZSetStorage<T> implements FlexRedisZSetStorage<T> {

    /**
     * 初始化Redis容器组件
     */
    public FlexRedisZSetStorageImpl() {
        super(null, null);
    }

    /**
     * 初始化Redis容器组件
     * 
     * @param name     容器名称
     * @param bizClass 元素业务类型
     */
    public FlexRedisZSetStorageImpl(String key, Class<T> bizClass) {
        super(key, bizClass);
    }

    @Override
    public long intersectAndStore(String key, String otherKey, String destKey) {
        return super.intersectAndStore(key(key), otherKey, destKey);
    }

    @Override
    public long intersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return super.intersectAndStore(key(key), otherKeys, destKey);
    }

    @Override
    public long unionAndStore(String key, String otherKey, String destKey) {
        return super.unionAndStore(key(key), otherKey, destKey);
    }

    @Override
    public long unionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return super.unionAndStore(key(key), otherKeys, destKey);
    }

    @Override
    public Set<T> range(String key, long start, long end) {
        return super.range(key(key), start, end);
    }

    @Override
    public Set<T> reverseRange(String key, long start, long end) {
        return super.reverseRange(key(key), start, end);
    }

    @Override
    public Set<TypedTuple<T>> rangeWithScores(String key, long start, long end) {
        return super.rangeWithScores(key(key), start, end);
    }

    @Override
    public Set<TypedTuple<T>> reverseRangeWithScores(String key, long start, long end) {
        return super.reverseRangeWithScores(key(key), start, end);
    }

    @Override
    public Set<T> rangeByLex(String key, Range range) {
        return super.rangeByLex(key(key), range);
    }

    @Override
    public Set<T> rangeByLex(String key, Range range, Limit limit) {
        return super.rangeByLex(key(key), range, limit);
    }

    @Override
    public Set<T> rangeByScore(String key, double min, double max) {
        return super.rangeByScore(key(key), min, max);
    }

    @Override
    public Set<T> rangeByScore(String key, double min, double max, long offset, long count) {
        return super.rangeByScore(key(key), min, max, offset, count);
    }

    @Override
    public Set<T> reverseRangeByScore(String key, double min, double max) {
        return super.reverseRangeByScore(key(key), min, max);
    }

    @Override
    public Set<T> reverseRangeByScore(String key, double min, double max, long offset, long count) {
        return super.reverseRangeByScore(key(key), min, max, offset, count);
    }

    @Override
    public Set<TypedTuple<T>> rangeByScoreWithScores(String key, double min, double max) {
        return super.rangeByScoreWithScores(key(key), min, max);
    }

    @Override
    public Set<TypedTuple<T>> rangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return super.rangeByScoreWithScores(key(key), min, max, offset, count);
    }

    @Override
    public Set<TypedTuple<T>> reverseRangeByScoreWithScores(String key, double min, double max) {
        return super.reverseRangeByScoreWithScores(key(key), min, max);
    }

    @Override
    public Set<TypedTuple<T>> reverseRangeByScoreWithScores(String key, double min, double max, long offset,
            long count) {
        return super.reverseRangeByScoreWithScores(key(key), min, max, offset, count);
    }

    @Override
    public boolean add(String key, T value, double score) {
        return super.add(key(key), value, score);
    }

    @Override
    public long add(String key, Set<TypedTuple<T>> tuples) {
        return super.add(key(key), tuples);
    }

    @Override
    public double incrementScore(String key, T value, double delta) {
        return super.incrementScore(key(key), value, delta);
    }

    @Override
    public long rank(String key, T o) {
        return super.rank(key(key), o);
    }

    @Override
    public long reverseRank(String key, T o) {
        return super.reverseRank(key(key), o);
    }

    @Override
    public double score(String key, T o) {
        return super.score(key(key), o);
    }

    @Override
    public long remove(String key, Collection<? extends T> values) {
        return super.remove(key(key), values);
    }

    @Override
    public long removeRange(String key, long start, long end) {
        return super.removeRange(key(key), start, end);
    }

    @Override
    public long removeRangeByScore(String key, double min, double max) {
        return super.removeRangeByScore(key(key), min, max);
    }

    @Override
    public long count(String key, double min, double max) {
        return super.count(key(key), min, max);
    }

    @Override
    public long size(String key) {
        return super.size(key(key));
    }

    @Override
    public long zCard(String key) {
        return super.zCard(key(key));
    }

    @Override
    public Set<T> set(String key) {
        return super.set(key(key));
    }

}
