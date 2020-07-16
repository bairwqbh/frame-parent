package per.cby.frame.redis.original.storage.set;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import per.cby.frame.redis.handler.RedisHandler;
import per.cby.frame.redis.original.storage.RedisComponent;

/**
 * Redis-集合组件
 * 
 * @author chenboyang
 *
 * @param <E> 元素类型
 */
@Deprecated
@SuppressWarnings("unchecked")
public class RedisSet<E> extends RedisComponent<String, E> implements Set<E> {

    /**
     * 构建方法
     * 
     * @param name 容器名称
     */
    public RedisSet(String name) {
        this.name = name;
    }

    /**
     * 构建方法
     * 
     * @param name    容器名称
     * @param handler Redis存储操作处理器
     */
    public RedisSet(String name, RedisHandler<String, E> handler) {
        this.name = name;
        this.redisHandler = handler;
    }

    /**
     * Redis哈希存储容器
     */
    private RedisSetStorage<E> redisSetStorage = new RedisSetStorage<E>() {
        @Override
        public String key() {
            return componentName();
        }

        @Override
        public RedisHandler<String, E> handler() {
            return getRedisHandler();
        }
    };

    @Override
    public int size() {
        return (int) redisSetStorage.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return redisSetStorage.isMember((E) o);
    }

    @Override
    public Iterator<E> iterator() {
        return redisSetStorage.members().iterator();
    }

    @Override
    public Object[] toArray() {
        return redisSetStorage.members().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return redisSetStorage.members().toArray(a);
    }

    @Override
    public boolean add(E e) {
        return redisSetStorage.add(e) > 0;
    }

    @Override
    public boolean remove(Object o) {
        Set<E> set = new HashSet<E>(1);
        set.add((E) o);
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
    public boolean addAll(Collection<? extends E> c) {
        return redisSetStorage.addAll(c) > 0;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return redisSetStorage.members().retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return redisSetStorage.remove((Collection<? extends E>) c) > 0;
    }

    @Override
    public void clear() {
        redisSetStorage.clear();
    }

}
