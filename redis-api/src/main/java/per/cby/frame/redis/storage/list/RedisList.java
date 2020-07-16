package per.cby.frame.redis.storage.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections4.CollectionUtils;

import per.cby.frame.redis.storage.AbstractRedisBaseStorage;
import per.cby.frame.redis.storage.RedisComponent;

/**
 * Redis列表组件
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
@SuppressWarnings({ "unchecked", "hiding" })
public class RedisList<T> extends RedisComponent<String, T> implements List<T> {

    /** Redis列表存储容器 */
    private final DefaultRedisListStorage<T> storage;

    /**
     * 构建容器
     * 
     * @param name     容器名称
     * @param bizClass 元素业务类型
     */
    public RedisList(String name, Class<T> bizClass) {
        storage = new DefaultRedisListStorage<T>(name, bizClass);
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
        return storage.list().contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return storage.list().iterator();
    }

    @Override
    public Object[] toArray() {
        return storage.list().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return storage.list().toArray(a);
    }

    @Override
    public boolean add(T e) {
        return storage.leftPush(e) > 0;
    }

    @Override
    public boolean remove(Object o) {
        return storage.remove(0, (T) o) > 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return storage.list().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return storage.leftPushAll(c) > 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
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
        return storage.list().retainAll(c);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public T get(int index) {
        return storage.index(index);
    }

    @Override
    public T set(int index, T element) {
        T e = get(index);
        storage.set(index, element);
        return e;
    }

    @Override
    public void add(int index, T element) {
        storage.leftPush(element);
    }

    @Override
    public T remove(int index) {
        T e = get(index);
        if (e != null) {
            remove(e);
        }
        return e;
    }

    @Override
    public int indexOf(Object o) {
        return storage.list().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return storage.list().lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return storage.list().listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return storage.list().listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return storage.list().subList(fromIndex, toIndex);
    }

}
