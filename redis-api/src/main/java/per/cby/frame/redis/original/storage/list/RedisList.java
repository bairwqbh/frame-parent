package per.cby.frame.redis.original.storage.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections4.CollectionUtils;

import per.cby.frame.redis.handler.RedisHandler;
import per.cby.frame.redis.original.storage.RedisComponent;

/**
 * Redis列表组件
 * 
 * @author chenboyang
 *
 * @param <E> 元素类型
 */
@Deprecated
@SuppressWarnings("unchecked")
public class RedisList<E> extends RedisComponent<String, E> implements List<E> {

    /**
     * 构建方法
     * 
     * @param name 容器名称
     */
    public RedisList(String name) {
        this.name = name;
    }

    /**
     * 构建方法
     * 
     * @param name    容器名称
     * @param handler Redis存储操作处理器
     */
    public RedisList(String name, RedisHandler<String, E> handler) {
        this.name = name;
        this.redisHandler = handler;
    }

    /**
     * Redis哈希存储容器
     */
    private RedisListStorage<E> redisListStorage = new RedisListStorage<E>() {
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
        return (int) redisListStorage.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return redisListStorage.list().contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return redisListStorage.list().iterator();
    }

    @Override
    public Object[] toArray() {
        return redisListStorage.list().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return redisListStorage.list().toArray(a);
    }

    @Override
    public boolean add(E e) {
        return redisListStorage.leftPush(e) > 0;
    }

    @Override
    public boolean remove(Object o) {
        return redisListStorage.remove(0, (E) o) > 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return redisListStorage.list().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return redisListStorage.leftPushAll(c) > 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (CollectionUtils.isNotEmpty(c)) {
            c.forEach(this::remove);
            return true;
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return redisListStorage.list().retainAll(c);
    }

    @Override
    public void clear() {
        redisListStorage.clear();
    }

    @Override
    public E get(int index) {
        return redisListStorage.index(index);
    }

    @Override
    public E set(int index, E element) {
        E e = get(index);
        redisListStorage.set(index, element);
        return e;
    }

    @Override
    public void add(int index, E element) {
        redisListStorage.leftPush(element);
    }

    @Override
    public E remove(int index) {
        E e = get(index);
        if (e != null) {
            remove(e);
        }
        return e;
    }

    @Override
    public int indexOf(Object o) {
        return redisListStorage.list().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return redisListStorage.list().lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return redisListStorage.list().listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return redisListStorage.list().listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return redisListStorage.list().subList(fromIndex, toIndex);
    }

}
