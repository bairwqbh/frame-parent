package per.cby.frame.redis.storage.set;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import per.cby.frame.redis.storage.AbstractRedisBaseStorage;
import per.cby.frame.redis.storage.RedisComponent;

/**
 * Redis-集合组件
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
@SuppressWarnings({ "unchecked", "hiding" })
public class RedisSet<T> extends RedisComponent<String, T> implements Set<T> {

    /** Redis哈希存储容器 */
    private final DefaultRedisSetStorage<T> storage;

    /**
     * 构建容器
     * 
     * @param name     容器名称
     * @param bizClass 元素业务类型
     */
    public RedisSet(String name, Class<T> bizClass) {
        storage = new DefaultRedisSetStorage<T>(name, bizClass);
    }

    @Override
    public AbstractRedisBaseStorage<String, T> storage() {
        return storage;
    }

    @Override
    public int size() {
        return (int) storage.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return storage.isMember((T) o);
    }

    @Override
    public Iterator<T> iterator() {
        return storage.members().iterator();
    }

    @Override
    public Object[] toArray() {
        return storage.members().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return storage.members().toArray(a);
    }

    @Override
    public boolean add(T e) {
        return storage.add(e) > 0;
    }

    @Override
    public boolean remove(Object o) {
        Set<T> set = new HashSet<T>(1);
        set.add((T) o);
        return removeAll(set);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (CollectionUtils.isEmpty(c)) {
            return false;
        }
        for (Object object : c) {
            if (!contains(object)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return storage.addAll(c) > 0;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return storage.members().retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return storage.remove((Collection<? extends T>) c) > 0;
    }

    @Override
    public void clear() {
        storage.clear();
    }

}
