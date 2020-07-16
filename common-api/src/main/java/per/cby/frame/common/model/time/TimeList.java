package per.cby.frame.common.model.time;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;

/**
 * 时间列表容器接口
 * 
 * @author chenboyang
 *
 * @param <E> 列表元素类型
 */
public interface TimeList<E> extends List<E> {

    /**
     * 获取监听器延迟执行时间
     * 
     * @return 延迟时间
     */
    long delay();

    /**
     * 获取监听器执行间隔时间
     * 
     * @return 间隔时间
     */
    long interval();

    /**
     * 获取时间元素默认过期时间
     * 
     * @return 过期时间
     */
    long timeout();

    /**
     * 获取监听器执行时间单位和时间元素默认时间单位
     * 
     * @return 时间单位
     */
    TimeUnit timeUnit();

    /**
     * 获取时间元素容器
     * 
     * @return 时间元素容器
     */
    List<TimeElement<E>> container();

    /**
     * 获取业务元素列表
     * 
     * @return 业务元素列表
     */
    default List<E> elementList() {
        return elementStream().collect(Collectors.toList());
    }

    /**
     * 获取业务元素流
     * 
     * @return 业务元素流
     */
    default Stream<E> elementStream() {
        return container().stream().map((element) -> element.getElement());
    }

    /**
     * 向时间列表中添加元素并设置过期时间
     * 
     * @param element 元素
     * @param timeout 过期时间
     * @return 添加是否成功
     */
    default boolean add(E element, long timeout) {
        return add(element, timeout, timeUnit());
    }

    /**
     * 向时间列表中添加元素并设置过期时间
     * 
     * @param element  元素
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @return 添加是否成功
     */
    default boolean add(E element, long timeout, TimeUnit timeUnit) {
        return container().add(new TimeElement<E>(element, timeout, timeUnit));
    }

    /**
     * 向时间列表中添加元素并设置过期时间
     * 
     * @param index   下标
     * @param element 元素
     * @param timeout 过期时间
     */
    default void add(int index, E element, long timeout) {
        add(index, element, timeout, timeUnit());
    }

    /**
     * 向时间列表中添加元素并设置过期时间
     * 
     * @param index    下标
     * @param element  元素
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     */
    default void add(int index, E element, long timeout, TimeUnit timeUnit) {
        container().add(index, new TimeElement<E>(element, timeout, timeUnit));
    }

    /**
     * 将集合转换为时间列表
     * 
     * @param c        集合
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @return 时间列表
     */
    default List<TimeElement<E>> convertTimeList(Collection<? extends E> c, long timeout, TimeUnit timeUnit) {
        return c.stream().map(element -> new TimeElement<E>(element, timeout, timeUnit)).collect(Collectors.toList());
    }

    /**
     * 将集合批量添加到列表中
     * 
     * @param c        集合
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @return 是否添加成功
     */
    default boolean addAll(Collection<? extends E> c, long timeout, TimeUnit timeUnit) {
        if (CollectionUtils.isNotEmpty(c)) {
            return container().addAll(convertTimeList(c, timeout, timeUnit));
        }
        return false;
    }

    /**
     * 将集合从指定下标批量添加到列表中
     * 
     * @param index    下标
     * @param c        集合
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @return 是否添加成功
     */
    default boolean addAll(int index, Collection<? extends E> c, long timeout, TimeUnit timeUnit) {
        if (CollectionUtils.isNotEmpty(c)) {
            return container().addAll(index, convertTimeList(c, timeout, timeUnit));
        }
        return false;
    }

    /**
     * 根据集合查询列表元素
     * 
     * @param c 集合
     * @return 元素列表
     */
    default List<TimeElement<E>> findElement(Collection<?> c) {
        return container().stream().filter((element) -> c.contains(element.getElement())).collect(Collectors.toList());
    }

    /**
     * 设置列表中的元素
     * 
     * @param index   下标
     * @param element 元素
     * @param timeout 过期时间
     * @return 设置前的旧元素
     */
    default E set(int index, E element, long timeout) {
        return set(index, element, timeout);
    }

    /**
     * 设置列表中的元素
     * 
     * @param index    下标
     * @param element  元素
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @return 设置前的旧元素
     */
    default E set(int index, E element, long timeout, TimeUnit timeUnit) {
        return container().set(index, new TimeElement<E>(element, timeout, timeUnit)).getElement();
    }

    @Override
    default int size() {
        return container().size();
    }

    @Override
    default boolean isEmpty() {
        return container().isEmpty();
    }

    @Override
    default boolean contains(Object o) {
        if (o != null && CollectionUtils.isNotEmpty(container())) {
            for (TimeElement<E> timeElement : container()) {
                if (o.equals(timeElement.getElement())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    default Iterator<E> iterator() {
        return elementStream().iterator();
    }

    @Override
    default Object[] toArray() {
        return elementStream().toArray();
    }

    @Override
    default <T> T[] toArray(T[] a) {
        return elementList().toArray(a);
    }

    @Override
    default boolean add(E e) {
        return add(e, timeout());
    }

    @Override
    default boolean remove(Object o) {
        if (o != null && CollectionUtils.isNotEmpty(container())) {
            for (TimeElement<E> timeElement : container()) {
                if (o.equals(timeElement.getElement())) {
                    return container().remove(timeElement);
                }
            }
        }
        return false;
    }

    @Override
    default boolean containsAll(Collection<?> c) {
        return elementList().containsAll(c);
    }

    @Override
    default boolean addAll(Collection<? extends E> c) {
        if (CollectionUtils.isNotEmpty(c)) {
            return addAll(c, timeout(), timeUnit());
        }
        return false;
    }

    @Override
    default boolean addAll(int index, Collection<? extends E> c) {
        if (CollectionUtils.isNotEmpty(c)) {
            return addAll(index, c, timeout(), timeUnit());
        }
        return false;
    }

    @Override
    default boolean removeAll(Collection<?> c) {
        if (CollectionUtils.isNotEmpty(c)) {
            return container().removeAll(findElement(c));
        }
        return false;
    }

    @Override
    default boolean retainAll(Collection<?> c) {
        if (CollectionUtils.isNotEmpty(c)) {
            return container().retainAll(findElement(c));
        }
        return false;
    }

    @Override
    default void clear() {
        container().clear();
    }

    @Override
    default E get(int index) {
        return container().get(index).getElement();
    }

    @Override
    default E set(int index, E element) {
        return set(index, element, timeout());
    }

    @Override
    default void add(int index, E element) {
        add(index, element, timeout());
    }

    @Override
    default E remove(int index) {
        TimeElement<E> timeElement = container().remove(index);
        if (timeElement != null) {
            return timeElement.getElement();
        }
        return null;
    }

    @Override
    default int indexOf(Object o) {
        return elementList().indexOf(o);
    }

    @Override
    default int lastIndexOf(Object o) {
        return elementList().lastIndexOf(o);
    }

    @Override
    default ListIterator<E> listIterator() {
        return elementList().listIterator();
    }

    @Override
    default ListIterator<E> listIterator(int index) {
        return elementList().listIterator(index);
    }

    @Override
    default List<E> subList(int fromIndex, int toIndex) {
        return elementList().subList(fromIndex, toIndex);
    }

}
