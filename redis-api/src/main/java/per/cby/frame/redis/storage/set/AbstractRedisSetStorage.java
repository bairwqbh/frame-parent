package per.cby.frame.redis.storage.set;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.support.collections.RedisCollectionFactoryBean.CollectionType;

import per.cby.frame.redis.storage.AbstractRedisContainerStorage;

/**
 * Redis集合存储接口抽象实现
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
@SuppressWarnings("unchecked")
public abstract class AbstractRedisSetStorage<T> extends AbstractRedisContainerStorage<String, T>
        implements FlexRedisSetStorage<T> {

    /**
     * 初始化Redis容器组件
     * 
     * @param name     容器名称
     * @param bizClass 元素业务类型
     */
    public AbstractRedisSetStorage(String name, Class<T> bizClass) {
        super(name, String.class, bizClass);
    }

    /**
     * 获取列表操作器
     * 
     * @return 哈希操作器
     */
    public SetOperations<String, String> ops() {
        return template().opsForSet();
    }

    @Override
    public Set<T> difference(String key, String otherKey) {
        return valueDeserialize(ops().difference(key, otherKey), CollectionType.SET);
    }

    @Override
    public Set<T> difference(String key, Collection<String> otherKeys) {
        return valueDeserialize(ops().difference(key, otherKeys), CollectionType.SET);
    }

    @Override
    public long differenceAndStore(String key, String otherKey, String destKey) {
        return Optional.ofNullable(ops().differenceAndStore(key, otherKey, destKey)).orElse(0L);
    }

    @Override
    public long differenceAndStore(String key, Collection<String> otherKeys, String destKey) {
        return Optional.ofNullable(ops().differenceAndStore(key, otherKeys, destKey)).orElse(0L);
    }

    @Override
    public Set<T> intersect(String key, String otherKey) {
        return valueDeserialize(ops().intersect(key, otherKey), CollectionType.SET);
    }

    @Override
    public Set<T> intersect(String key, Collection<String> otherKeys) {
        return valueDeserialize(ops().intersect(key, otherKeys), CollectionType.SET);
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
    public Set<T> union(String key, String otherKey) {
        return valueDeserialize(ops().union(key, otherKey), CollectionType.SET);
    }

    @Override
    public Set<T> union(String key, Collection<String> otherKeys) {
        return valueDeserialize(ops().union(key, otherKeys), CollectionType.SET);
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
    public long add(String key, T... values) {
        return Optional.ofNullable(ops().add(key, valueSerialize(values))).orElse(0L);
    }

    @Override
    public long addAll(String key, Collection<? extends T> values) {
        return Optional.ofNullable(add(key, (T[]) values.toArray())).orElse(0L);
    }

    @Override
    public boolean isMember(String key, T o) {
        return Optional.ofNullable(ops().isMember(key, o)).orElse(false);
    }

    @Override
    public Set<T> members(String key) {
        return valueDeserialize(ops().members(key), CollectionType.SET);
    }

    @Override
    public boolean move(String key, T value, String destKey) {
        return Optional.ofNullable(ops().move(key, valueSerialize(value), destKey)).orElse(false);
    }

    @Override
    public T randomMember(String key) {
        return valueDeserialize(ops().randomMember(key));
    }

    @Override
    public Set<T> distinctRandomMembers(String key, long count) {
        return valueDeserialize(ops().distinctRandomMembers(key, count), CollectionType.SET);
    }

    @Override
    public List<T> randomMembers(String key, long count) {
        return valueDeserialize(ops().randomMembers(key, count), CollectionType.LIST);
    }

    @Override
    public long remove(String key, Collection<? extends T> values) {
        return Optional.ofNullable(ops().remove(key, values.toArray())).orElse(0L);
    }

    @Override
    public T pop(String key) {
        return valueDeserialize(ops().pop(key));
    }

    @Override
    public long size(String key) {
        return Optional.ofNullable(ops().size(key)).orElse(0L);
    }

}
