package per.cby.frame.redis.storage.zset;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.redis.connection.RedisZSetCommands.Limit;
import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.support.collections.RedisCollectionFactoryBean.CollectionType;

import per.cby.frame.redis.storage.AbstractRedisContainerStorage;

/**
 * Redis有序集合存储接口
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractRedisZSetStorage<T> extends AbstractRedisContainerStorage<String, T>
        implements FlexRedisZSetStorage<T> {

    /**
     * 初始化Redis容器组件
     * 
     * @param name     容器名称
     * @param bizClass 元素业务类型
     */
    public AbstractRedisZSetStorage(String key, Class<T> bizClass) {
        super(key, String.class, bizClass);
    }

    /**
     * 获取列表操作器
     * 
     * @return 哈希操作器
     */
    public ZSetOperations<String, String> ops() {
        return template().opsForZSet();
    }

    /**
     * 反序列化元组集合
     * 
     * @param set 元组集合
     * @return 元组集合
     */
    public Set<TypedTuple<T>> deserialize(Set<TypedTuple<String>> set) {
        return set.stream().map(new Function<TypedTuple<String>, DefaultTypedTuple<T>>() {
            @Override
            public DefaultTypedTuple<T> apply(TypedTuple<String> t) {
                return new DefaultTypedTuple(valueDeserialize(t.getValue()), t.getScore());
            }
        }).collect(Collectors.toSet());
    }

    @Override
    public long intersectAndStore(String key, String otherKey, String destKey) {
        return Optional.ofNullable(ops().intersectAndStore(key, otherKey, destKey)).orElse(0L);
    }

    @Override
    public long intersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return Optional.ofNullable(ops().intersectAndStore(key, otherKeys, destKey)).orElse(0L);
    }

    @Override
    public long unionAndStore(String key, String otherKey, String destKey) {
        return Optional.ofNullable(ops().unionAndStore(key, otherKey, destKey)).orElse(0L);
    }

    @Override
    public long unionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return Optional.ofNullable(ops().unionAndStore(key, otherKeys, destKey)).orElse(0L);
    }

    @Override
    public Set<T> range(String key, long start, long end) {
        return valueDeserialize(ops().range(key, start, end), CollectionType.SET).stream().filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<T> reverseRange(String key, long start, long end) {
        return valueDeserialize(ops().reverseRange(key, start, end), CollectionType.SET).stream()
                .filter(Objects::nonNull).collect(Collectors.toSet());
    }

    @Override
    public Set<TypedTuple<T>> rangeWithScores(String key, long start, long end) {
        return deserialize(ops().rangeWithScores(key, start, end)).stream().filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TypedTuple<T>> reverseRangeWithScores(String key, long start, long end) {
        return deserialize(ops().reverseRangeWithScores(key, start, end)).stream().filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<T> rangeByLex(String key, Range range) {
        return valueDeserialize(ops().rangeByLex(key, range), CollectionType.SET).stream().filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<T> rangeByLex(String key, Range range, Limit limit) {
        return valueDeserialize(ops().rangeByLex(key, range, limit), CollectionType.SET).stream()
                .filter(Objects::nonNull).collect(Collectors.toSet());
    }

    @Override
    public Set<T> rangeByScore(String key, double min, double max) {
        return valueDeserialize(ops().rangeByScore(key, min, max), CollectionType.SET).stream().filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<T> rangeByScore(String key, double min, double max, long offset, long count) {
        return valueDeserialize(ops().rangeByScore(key, min, max, offset, count), CollectionType.SET).stream()
                .filter(Objects::nonNull).collect(Collectors.toSet());
    }

    @Override
    public Set<T> reverseRangeByScore(String key, double min, double max) {
        return valueDeserialize(ops().reverseRangeByScore(key, min, max), CollectionType.SET).stream()
                .filter(Objects::nonNull).collect(Collectors.toSet());
    }

    @Override
    public Set<T> reverseRangeByScore(String key, double min, double max, long offset, long count) {
        return valueDeserialize(ops().reverseRangeByScore(key, min, max, offset, count), CollectionType.SET).stream()
                .filter(Objects::nonNull).collect(Collectors.toSet());
    }

    @Override
    public Set<TypedTuple<T>> rangeByScoreWithScores(String key, double min, double max) {
        return deserialize(ops().rangeByScoreWithScores(key, min, max)).stream().filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TypedTuple<T>> rangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return deserialize(ops().rangeByScoreWithScores(key, min, max, offset, count)).stream().filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TypedTuple<T>> reverseRangeByScoreWithScores(String key, double min, double max) {
        return deserialize(ops().reverseRangeByScoreWithScores(key, min, max)).stream().filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TypedTuple<T>> reverseRangeByScoreWithScores(String key, double min, double max, long offset,
            long count) {
        return deserialize(ops().reverseRangeByScoreWithScores(key, min, max, offset, count)).stream()
                .filter(Objects::nonNull).collect(Collectors.toSet());
    }

    @Override
    public boolean add(String key, T value, double score) {
        return Optional.ofNullable(ops().add(key, valueSerialize(value), score)).orElse(false);
    }

    @Override
    public long add(String key, Set<TypedTuple<T>> tuples) {
        return Optional.ofNullable(
                ops().add(key, tuples.stream().map(new Function<TypedTuple<T>, DefaultTypedTuple<String>>() {
                    @Override
                    public DefaultTypedTuple<String> apply(TypedTuple<T> t) {
                        return new DefaultTypedTuple(valueSerialize(t.getValue()), t.getScore());
                    }
                }).collect(Collectors.toSet()))).orElse(0L);
    }

    @Override
    public double incrementScore(String key, T value, double delta) {
        return Optional.ofNullable(ops().incrementScore(key, valueSerialize(value), delta)).orElse(0D);
    }

    @Override
    public long rank(String key, T o) {
        return Optional.ofNullable(ops().rank(key, o)).orElse(0L);
    }

    @Override
    public long reverseRank(String key, T o) {
        return Optional.ofNullable(ops().reverseRank(key, o)).orElse(0L);
    }

    @Override
    public double score(String key, T o) {
        return Optional.ofNullable(ops().score(key, o)).orElse(0D);
    }

    @Override
    public long remove(String key, Collection<? extends T> values) {
        return Optional.ofNullable(ops().remove(key, values.toArray())).orElse(0L);
    }

    @Override
    public long removeRange(String key, long start, long end) {
        return Optional.ofNullable(ops().removeRange(key, start, end)).orElse(0L);
    }

    @Override
    public long removeRangeByScore(String key, double min, double max) {
        return Optional.ofNullable(ops().removeRangeByScore(key, min, max)).orElse(0L);
    }

    @Override
    public long count(String key, double min, double max) {
        return Optional.ofNullable(ops().count(key, min, max)).orElse(0L);
    }

    @Override
    public long size(String key) {
        return Optional.ofNullable(ops().size(key)).orElse(0L);
    }

    @Override
    public long zCard(String key) {
        return Optional.ofNullable(ops().zCard(key)).orElse(0L);
    }

    @Override
    public Set<T> set(String key) {
        return range(key, 0, -1);
    }

}
