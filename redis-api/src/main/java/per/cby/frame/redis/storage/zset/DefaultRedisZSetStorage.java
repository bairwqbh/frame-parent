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
public class DefaultRedisZSetStorage<T> extends AbstractRedisZSetStorage<T> implements RedisZSetStorage<T> {

    /**
     * 初始化Redis容器组件
     */
    public DefaultRedisZSetStorage() {
        super(null, null);
    }

    /**
     * 初始化Redis容器组件
     * 
     * @param name     容器名称
     * @param bizClass 元素业务类型
     */
    public DefaultRedisZSetStorage(String key, Class<T> bizClass) {
        super(key, bizClass);
    }

    @Override
    public long intersectAndStore(String otherKey, String destKey) {
        return super.intersectAndStore(key(), otherKey, destKey);
    }

    @Override
    public long intersectAndStore(Collection<String> otherKeys, String destKey) {
        return super.intersectAndStore(key(), otherKeys, destKey);
    }

    @Override
    public long unionAndStore(String otherKey, String destKey) {
        return super.unionAndStore(key(), otherKey, destKey);
    }

    @Override
    public long unionAndStore(Collection<String> otherKeys, String destKey) {
        return super.unionAndStore(key(), otherKeys, destKey);
    }

    @Override
    public Set<T> range(long start, long end) {
        return super.range(key(), start, end);
    }

    @Override
    public Set<T> reverseRange(long start, long end) {
        return super.reverseRange(key(), start, end);
    }

    @Override
    public Set<TypedTuple<T>> rangeWithScores(long start, long end) {
        return super.rangeWithScores(key(), start, end);
    }

    @Override
    public Set<TypedTuple<T>> reverseRangeWithScores(long start, long end) {
        return super.reverseRangeWithScores(key(), start, end);
    }

    @Override
    public Set<T> rangeByLex(Range range) {
        return super.rangeByLex(key(), range);
    }

    @Override
    public Set<T> rangeByLex(Range range, Limit limit) {
        return super.rangeByLex(key(), range, limit);
    }

    @Override
    public Set<T> rangeByScore(double min, double max) {
        return super.rangeByScore(key(), min, max);
    }

    @Override
    public Set<T> rangeByScore(double min, double max, long offset, long count) {
        return super.rangeByScore(key(), min, max, offset, count);
    }

    @Override
    public Set<T> reverseRangeByScore(double min, double max) {
        return super.reverseRangeByScore(key(), min, max);
    }

    @Override
    public Set<T> reverseRangeByScore(double min, double max, long offset, long count) {
        return super.reverseRangeByScore(key(), min, max, offset, count);
    }

    @Override
    public Set<TypedTuple<T>> rangeByScoreWithScores(double min, double max) {
        return super.rangeByScoreWithScores(key(), min, max);
    }

    @Override
    public Set<TypedTuple<T>> rangeByScoreWithScores(double min, double max, long offset, long count) {
        return super.rangeByScoreWithScores(key(), min, max, offset, count);
    }

    @Override
    public Set<TypedTuple<T>> reverseRangeByScoreWithScores(double min, double max) {
        return super.reverseRangeByScoreWithScores(key(), min, max);
    }

    @Override
    public Set<TypedTuple<T>> reverseRangeByScoreWithScores(double min, double max, long offset, long count) {
        return super.reverseRangeByScoreWithScores(key(), min, max, offset, count);
    }

    @Override
    public boolean add(T value, double score) {
        return super.add(key(), value, score);
    }

    @Override
    public long add(Set<TypedTuple<T>> tuples) {
        return super.add(key(), tuples);
    }

    @Override
    public double incrementScore(T value, double delta) {
        return super.incrementScore(key(), value, delta);
    }

    @Override
    public long rank(T o) {
        return super.rank(key(), o);
    }

    @Override
    public long reverseRank(T o) {
        return super.reverseRank(key(), o);
    }

    @Override
    public double score(T o) {
        return super.score(key(), o);
    }

    @Override
    public long remove(Collection<? extends T> values) {
        return super.remove(key(), values);
    }

    @Override
    public long removeRange(long start, long end) {
        return super.removeRange(key(), start, end);
    }

    @Override
    public long removeRangeByScore(double min, double max) {
        return super.removeRangeByScore(key(), min, max);
    }

    @Override
    public long count(double min, double max) {
        return super.count(key(), min, max);
    }

    @Override
    public long size() {
        return super.size(key());
    }

    @Override
    public long zCard() {
        return super.zCard(key());
    }

    @Override
    public Set<T> set() {
        return super.set(key());
    }

}
