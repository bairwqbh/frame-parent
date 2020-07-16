package per.cby.frame.redis.original.storage.zset;

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

import per.cby.frame.redis.handler.RedisHandler;
import per.cby.frame.redis.original.storage.RedisComponent;

/**
 * Redis-有序集合组件
 * 
 * @author chenboyang
 *
 * @param <E> 元素类型
 */
@Deprecated
@SuppressWarnings("unchecked")
public class RedisSortedSet<E> extends RedisComponent<String, E> implements SortedSet<E> {

    /** 双精度函数 */
    private ToDoubleFunction<E> function;

    /**
     * 构建方法
     * 
     * @param name     容器名称
     * @param function 双精度函数
     */
    public RedisSortedSet(String name, ToDoubleFunction<E> function) {
        this.name = name;
        this.function = function;
    }

    /**
     * 构建方法
     * 
     * @param name     容器名称
     * @param function 双精度函数
     * @param handler  Redis存储操作处理器
     */
    public RedisSortedSet(String name, ToDoubleFunction<E> function, RedisHandler<String, E> handler) {
        this.name = name;
        this.function = function;
        this.redisHandler = handler;
    }

    /**
     * Redis哈希存储容器
     */
    private RedisZSetStorage<E> redisZSetStorage = new RedisZSetStorage<E>() {
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
        return (int) redisZSetStorage.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return redisZSetStorage.rank((E) o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return redisZSetStorage.set().iterator();
    }

    @Override
    public Object[] toArray() {
        return redisZSetStorage.set().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return redisZSetStorage.set().toArray(a);
    }

    @Override
    public boolean add(E e) {
        return redisZSetStorage.add(e, function.applyAsDouble(e));
    }

    @Override
    public boolean remove(Object o) {
        Set<E> set = new HashSet<E>(1);
        set.add((E) o);
        return removeAll(set);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return redisZSetStorage.set().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return redisZSetStorage.add(c.stream().map(e -> new DefaultTypedTuple<E>(e, function.applyAsDouble(e)))
                .collect(Collectors.toSet())) > 0;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Set<?> set = redisZSetStorage.set();
        set.removeAll(c);
        return removeAll(set);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return redisZSetStorage.remove((Collection<? extends E>) c) > 0;
    }

    @Override
    public void clear() {
        redisZSetStorage.clear();
    }

    @Override
    public Comparator<? super E> comparator() {
        return Comparator.comparingDouble(function);
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return new TreeSet<E>(
                redisZSetStorage.rangeByScore(redisZSetStorage.score(fromElement), redisZSetStorage.score(toElement)));
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return new TreeSet<E>(redisZSetStorage.rangeByScore(Double.MIN_VALUE, redisZSetStorage.score(toElement)));
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return new TreeSet<E>(redisZSetStorage.rangeByScore(redisZSetStorage.score(fromElement), Double.MAX_VALUE));
    }

    @Override
    public E first() {
        return redisZSetStorage.range(0, 1).iterator().next();
    }

    @Override
    public E last() {
        long size = redisZSetStorage.size();
        return redisZSetStorage.range(size - 1, size).iterator().next();
    }

}
