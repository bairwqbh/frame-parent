package per.cby.frame.redis.storage.set;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Redis集合存储接口抽象实现
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
@SuppressWarnings("unchecked")
public class DefaultRedisSetStorage<T> extends AbstractRedisSetStorage<T> implements RedisSetStorage<T> {

    /**
     * 初始化Redis容器组件
     */
    public DefaultRedisSetStorage() {
        super(null, null);
    }

    /**
     * 初始化Redis容器组件
     * 
     * @param name     容器名称
     * @param bizClass 元素业务类型
     */
    public DefaultRedisSetStorage(String name, Class<T> bizClass) {
        super(name, bizClass);
    }

    @Override
    public Set<T> difference(String otherKey) {
        return super.difference(key(), otherKey);
    }

    @Override
    public Set<T> difference(Collection<String> otherKeys) {
        return super.difference(key(), otherKeys);
    }

    @Override
    public long differenceAndStore(String otherKey, String destKey) {
        return super.differenceAndStore(key(), otherKey, destKey);
    }

    @Override
    public long differenceAndStore(Collection<String> otherKeys, String destKey) {
        return super.differenceAndStore(key(), otherKeys, destKey);
    }

    @Override
    public Set<T> intersect(String otherKey) {
        return super.intersect(key(), otherKey);
    }

    @Override
    public Set<T> intersect(Collection<String> otherKeys) {
        return super.intersect(key(), otherKeys);
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
    public Set<T> union(String otherKey) {
        return super.union(key(), otherKey);
    }

    @Override
    public Set<T> union(Collection<String> otherKeys) {
        return super.union(key(), otherKeys);
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
    public long add(T... values) {
        return super.add(key(), values);
    }

    @Override
    public long addAll(Collection<? extends T> values) {
        return super.addAll(key(), values);
    }

    @Override
    public boolean isMember(T o) {
        return super.isMember(key(), o);
    }

    @Override
    public Set<T> members() {
        return super.members(key());
    }

    @Override
    public boolean move(T value, String destKey) {
        return super.move(key(), value, destKey);
    }

    @Override
    public T randomMember() {
        return super.randomMember(key());
    }

    @Override
    public Set<T> distinctRandomMembers(long count) {
        return super.distinctRandomMembers(key(), count);
    }

    @Override
    public List<T> randomMembers(long count) {
        return super.randomMembers(key(), count);
    }

    @Override
    public long remove(Collection<? extends T> values) {
        return super.remove(key(), values);
    }

    @Override
    public T pop() {
        return super.pop(key());
    }

    @Override
    public long size() {
        return super.size(key());
    }

}
