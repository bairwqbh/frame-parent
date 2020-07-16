package per.cby.frame.redis.original.storage.set;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;

import per.cby.frame.redis.original.storage.RedisContainerStorage;

/**
 * Redis集合存储接口
 * 
 * @author chenboyang
 *
 * @param <E> 元素类型
 */
@Deprecated
@SuppressWarnings("unchecked")
public interface RedisSetStorage<E> extends RedisContainerStorage<String, E> {

    /**
     * 获取列表操作器
     * 
     * @return 哈希操作器
     */
    default SetOperations<String, E> ops() {
        return handler().opsForSet();
    }

    /**
     * 获取当前集合与其它集合的差集
     * 
     * @param otherKey 其它集合键
     * @return 差集
     */
    default Set<E> difference(String otherKey) {
        return ops().difference(key(), otherKey);
    }

    /**
     * 获取当前集合与其它多个集合的差集
     * 
     * @param otherKeys 其它集合键集
     * @return 差集
     */
    default Set<E> difference(Collection<String> otherKeys) {
        return ops().difference(key(), otherKeys);
    }

    /**
     * 将当前集合与其它集合的差集存入目标集合
     * 
     * @param otherKey 其它集合键
     * @param destKey  目标集合键
     * @return 存入集合长度
     */
    default long differenceAndStore(String otherKey, String destKey) {
        return Optional.ofNullable(ops().differenceAndStore(key(), otherKey, destKey)).orElse(0L);
    }

    /**
     * 将当前集合与其它多个集合的差集存入目标集合
     * 
     * @param otherKeys 其它集合键集
     * @param destKey   目标集合键
     * @return 存入集合长度
     */
    default long differenceAndStore(Collection<String> otherKeys, String destKey) {
        return Optional.ofNullable(ops().differenceAndStore(key(), otherKeys, destKey)).orElse(0L);
    }

    /**
     * 获取当前集合与其它集合的交集
     * 
     * @param otherKey 其它集合键
     * @return 交集
     */
    default Set<E> intersect(String otherKey) {
        return ops().intersect(key(), otherKey);
    }

    /**
     * 获取当前集合与其它多个集合的交集
     * 
     * @param otherKeys 其它集合键集
     * @return 交集
     */
    default Set<E> intersect(Collection<String> otherKeys) {
        return ops().intersect(key(), otherKeys);
    }

    /**
     * 将当前集合与其它集合的交集存入目标集合
     * 
     * @param otherKey 其它集合键
     * @param destKey  目标集合键
     * @return 存入集合长度
     */
    default long intersectAndStore(String otherKey, String destKey) {
        return Optional.ofNullable(ops().intersectAndStore(key(), otherKey, destKey)).orElse(0L);
    }

    /**
     * 将当前集合与其它多个集合的交集存入目标集合
     * 
     * @param otherKeys 其它集合键集
     * @param destKey   目标集合键
     * @return 存入集合长度
     */
    default long intersectAndStore(Collection<String> otherKeys, String destKey) {
        return Optional.ofNullable(ops().intersectAndStore(key(), otherKeys, destKey)).orElse(0L);
    }

    /**
     * 获取当前集合与其它集合的并集
     * 
     * @param otherKey 其它集合键
     * @return 并集
     */
    default Set<E> union(String otherKey) {
        return ops().union(key(), otherKey);
    }

    /**
     * 获取当前集合与其它多个集合的并集
     * 
     * @param otherKeys 其它集合键集
     * @return 并集
     */
    default Set<E> union(Collection<String> otherKeys) {
        return ops().union(key(), otherKeys);
    }

    /**
     * 将当前集合与其它集合的并集存入目标集合
     * 
     * @param otherKey 其它集合键
     * @param destKey  目标集合键
     * @return 存入集合长度
     */
    default long unionAndStore(String otherKey, String destKey) {
        return Optional.ofNullable(ops().unionAndStore(key(), otherKey, destKey)).orElse(0L);
    }

    /**
     * 将当前集合与其它多个集合的并集存入目标集合
     * 
     * @param otherKeys 其它集合键集
     * @param destKey   目标集合键
     * @return 存入集合长度
     */
    default long unionAndStore(Collection<String> otherKeys, String destKey) {
        return Optional.ofNullable(ops().unionAndStore(key(), otherKeys, destKey)).orElse(0L);
    }

    /**
     * 新增元素
     * 
     * @param values 元素集合
     * @return 新增元素数量
     */
    default long add(E... values) {
        return Optional.ofNullable(ops().add(key(), values)).orElse(0L);
    }

    /**
     * 新增一组元素
     * 
     * @param values 元素集合
     * @return 新增元素数量
     */
    default long addAll(Collection<? extends E> values) {
        return Optional.ofNullable(add((E[]) values.toArray())).orElse(0L);
    }

    /**
     * 判断元素是否是集合的成员
     * 
     * @param o 元素
     * @return 是否是集合的成员
     */
    default boolean isMember(E o) {
        return Optional.ofNullable(ops().isMember(key(), o)).orElse(false);
    }

    /**
     * 获取集合中的所有元素
     * 
     * @return 元素集合
     */
    default Set<E> members() {
        return ops().members(key());
    }

    /**
     * 将元素从当前集合移动到目标集合
     * 
     * @param value   元素
     * @param destKey 目标集合键
     * @return 是否移动成功
     */
    default boolean move(E value, String destKey) {
        return Optional.ofNullable(ops().move(key(), value, destKey)).orElse(false);
    }

    /**
     * 随机获取集合中一个元素
     * 
     * @return 元素
     */
    default E randomMember() {
        return ops().randomMember(key());
    }

    /**
     * 随机获取集合中多个不重复元素
     * 
     * @param count 获取元素数量
     * @return 元素集合
     */
    default Set<E> distinctRandomMembers(long count) {
        return ops().distinctRandomMembers(key(), count);
    }

    /**
     * 随机获取集合中多个元素
     * 
     * @param count 获取元素数量
     * @return 元素集合
     */
    default List<E> randomMembers(long count) {
        return ops().randomMembers(key(), count);
    }

    /**
     * 移除集合中多个元素
     * 
     * @param values 元素集合
     * @return 移除元素数量
     */
    default long remove(Collection<? extends E> values) {
        return Optional.ofNullable(ops().remove(key(), values.toArray())).orElse(0L);
    }

    /**
     * 在集合中随机拉出一个元素
     * 
     * @return 元素
     */
    default E pop() {
        return ops().pop(key());
    }

    /**
     * 获取集合长度
     * 
     * @return 长度
     */
    default long size() {
        return Optional.ofNullable(ops().size(key())).orElse(0L);
    }

    /**
     * 扫描元素
     * 
     * @param options 扫描配置项
     * @return 元素光标
     */
    default Cursor<E> scan(ScanOptions options) {
        return ops().scan(key(), options);
    }

}
