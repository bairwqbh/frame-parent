package per.cby.frame.redis.storage.zset;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.DefaultTypedTuple;

import per.cby.frame.redis.storage.AbstractRedisBaseStorage;
import per.cby.frame.redis.storage.RedisComponent;

/**
 * Redis-有序集合组件
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
@SuppressWarnings({ "unchecked", "hiding" })
public class RedisSortedSet<T> extends RedisComponent<String, T> implements SortedSet<T> {

    /** 双精度函数 */
    private final ToDoubleFunction<T> function;

    /** Redis哈希存储容器 */
    private final DefaultRedisZSetStorage<T> storage;

    /**
     * 构建方法
     * 
     * @param name     容器名称
     * @param bizClass 元素业务类型
     * @param function 双精度函数
     */
    public RedisSortedSet(String name, Class<T> bizClass, ToDoubleFunction<T> function) {
        this.function = function;
        storage = new DefaultRedisZSetStorage<T>(name, bizClass);
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
        return storage.rank((T) o) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return storage.set().iterator();
    }

    @Override
    public Object[] toArray() {
        return storage.set().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return storage.set().toArray(a);
    }

    @Override
    public boolean add(T e) {
        return storage.add(e, function.applyAsDouble(e));
    }

    @Override
    public boolean remove(Object o) {
        Set<T> set = new HashSet<T>(1);
        set.add((T) o);
        return removeAll(set);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return storage.set().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return storage.add(c.stream().map(e -> new DefaultTypedTuple<T>(e, function.applyAsDouble(e)))
                .collect(Collectors.toSet())) > 0;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Set<?> set = storage.set();
        set.removeAll(c);
        return removeAll(set);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return storage.remove((Collection<? extends T>) c) > 0;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Comparator<? super T> comparator() {
        return Comparator.comparingDouble(function);
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return new TreeSet<T>(storage.rangeByScore(storage.score(fromElement), storage.score(toElement)));
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return new TreeSet<T>(storage.rangeByScore(Double.MIN_VALUE, storage.score(toElement)));
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new TreeSet<T>(storage.rangeByScore(storage.score(fromElement), Double.MAX_VALUE));
    }

    @Override
    public T first() {
        return storage.range(0, 1).iterator().next();
    }

    @Override
    public T last() {
        long size = storage.size();
        return storage.range(size - 1, size).iterator().next();
    }

}
