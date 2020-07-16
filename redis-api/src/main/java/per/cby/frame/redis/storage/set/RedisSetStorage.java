package per.cby.frame.redis.storage.set;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import per.cby.frame.redis.storage.RedisContainerStorage;

/**
 * Redis集合存储接口
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
@SuppressWarnings("unchecked")
public interface RedisSetStorage<T> extends RedisContainerStorage {

    /**
     * 获取当前集合与其它集合的差集
     * 
     * @param otherKey 其它集合键
     * @return 差集
     */
    Set<T> difference(String otherKey);

    /**
     * 获取当前集合与其它多个集合的差集
     * 
     * @param otherKeys 其它集合键集
     * @return 差集
     */
    Set<T> difference(Collection<String> otherKeys);

    /**
     * 将当前集合与其它集合的差集存入目标集合
     * 
     * @param otherKey 其它集合键
     * @param destKey  目标集合键
     * @return 存入集合长度
     */
    long differenceAndStore(String otherKey, String destKey);

    /**
     * 将当前集合与其它多个集合的差集存入目标集合
     * 
     * @param otherKeys 其它集合键集
     * @param destKey   目标集合键
     * @return 存入集合长度
     */
    long differenceAndStore(Collection<String> otherKeys, String destKey);

    /**
     * 获取当前集合与其它集合的交集
     * 
     * @param otherKey 其它集合键
     * @return 交集
     */
    Set<T> intersect(String otherKey);

    /**
     * 获取当前集合与其它多个集合的交集
     * 
     * @param otherKeys 其它集合键集
     * @return 交集
     */
    Set<T> intersect(Collection<String> otherKeys);

    /**
     * 将当前集合与其它集合的交集存入目标集合
     * 
     * @param otherKey 其它集合键
     * @param destKey  目标集合键
     * @return 存入集合长度
     */
    long intersectAndStore(String otherKey, String destKey);

    /**
     * 将当前集合与其它多个集合的交集存入目标集合
     * 
     * @param otherKeys 其它集合键集
     * @param destKey   目标集合键
     * @return 存入集合长度
     */
    long intersectAndStore(Collection<String> otherKeys, String destKey);

    /**
     * 获取当前集合与其它集合的并集
     * 
     * @param otherKey 其它集合键
     * @return 并集
     */
    Set<T> union(String otherKey);

    /**
     * 获取当前集合与其它多个集合的并集
     * 
     * @param otherKeys 其它集合键集
     * @return 并集
     */
    Set<T> union(Collection<String> otherKeys);

    /**
     * 将当前集合与其它集合的并集存入目标集合
     * 
     * @param otherKey 其它集合键
     * @param destKey  目标集合键
     * @return 存入集合长度
     */
    long unionAndStore(String otherKey, String destKey);

    /**
     * 将当前集合与其它多个集合的并集存入目标集合
     * 
     * @param otherKeys 其它集合键集
     * @param destKey   目标集合键
     * @return 存入集合长度
     */
    long unionAndStore(Collection<String> otherKeys, String destKey);

    /**
     * 新增元素
     * 
     * @param values 元素集合
     * @return 新增元素数量
     */
    long add(T... values);

    /**
     * 新增一组元素
     * 
     * @param values 元素集合
     * @return 新增元素数量
     */
    long addAll(Collection<? extends T> values);

    /**
     * 判断元素是否是集合的成员
     * 
     * @param o 元素
     * @return 是否是集合的成员
     */
    boolean isMember(T o);

    /**
     * 获取集合中的所有元素
     * 
     * @return 元素集合
     */
    Set<T> members();

    /**
     * 将元素从当前集合移动到目标集合
     * 
     * @param value   元素
     * @param destKey 目标集合键
     * @return 是否移动成功
     */
    boolean move(T value, String destKey);

    /**
     * 随机获取集合中一个元素
     * 
     * @return 元素
     */
    T randomMember();

    /**
     * 随机获取集合中多个不重复元素
     * 
     * @param count 获取元素数量
     * @return 元素集合
     */
    Set<T> distinctRandomMembers(long count);

    /**
     * 随机获取集合中多个元素
     * 
     * @param count 获取元素数量
     * @return 元素集合
     */
    List<T> randomMembers(long count);

    /**
     * 移除集合中多个元素
     * 
     * @param values 元素集合
     * @return 移除元素数量
     */
    long remove(Collection<? extends T> values);

    /**
     * 在集合中随机拉出一个元素
     * 
     * @return 元素
     */
    T pop();

    /**
     * 获取集合长度
     * 
     * @return 长度
     */
    long size();

}
