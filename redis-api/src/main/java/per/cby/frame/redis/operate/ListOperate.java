package per.cby.frame.redis.operate;

import java.util.Collection;
import java.util.List;

/**
 * Redis列表操作接口
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
@SuppressWarnings("unchecked")
public interface ListOperate<K, V> {

    /**
     * 获取列表集合
     * 
     * @param key 键
     * @return 列表集合
     */
    List<V> listGet(K key);

    /**
     * 获取指定范围内的列表集合
     * 
     * @param key   键
     * @param start 开始下标
     * @param end   结束下标
     * @return 列表集合
     */
    List<V> listRange(K key, long start, long end);

    /**
     * 删除指定范围外的元素
     * 
     * @param key   键
     * @param start 开始下标
     * @param end   结束下标
     */
    void listTrim(K key, long start, long end);

    /**
     * 在列表前面插入元素
     * 
     * @param key   键
     * @param value 元素
     * @return 列表当前长度
     */
    Long listLeftPush(K key, V value);

    /**
     * 在列表前面插入多条元素
     * 
     * @param key    键
     * @param values 元素集
     * @return 列表当前长度
     */
    Long listLeftPushAll(K key, V... values);

    /**
     * 在列表前面插入集合元素
     * 
     * @param key    键
     * @param values 元素集
     * @return 插入后的长度
     */
    Long listLeftPushAll(K key, Collection<V> values);

    /**
     * 在目标对象的前面插入元素
     * 
     * @param key   键
     * @param pivot 目标对象
     * @param value 元素
     * @return 列表当前长度
     */
    Long listLeftPush(K key, V pivot, V value);

    /**
     * 在列表后面插入元素
     * 
     * @param key   键
     * @param value 元素
     * @return 列表当前长度
     */
    Long listRightPush(K key, V value);

    /**
     * 在列表后面插入多条元素
     * 
     * @param key    键
     * @param values 元素集
     * @return 列表当前长度
     */
    Long listRightPushAll(K key, V... values);

    /**
     * 在列表后面插入集合元素
     * 
     * @param key    键
     * @param values 元素集
     * @return 列表当前长度
     */
    Long listRightPushAll(K key, Collection<V> values);

    /**
     * 在 目标对象的后面插入元素
     * 
     * @param key   键
     * @param pivot 目标对象
     * @param value 元素
     * @return 列表当前长度
     */
    Long listRightPush(K key, V pivot, V value);

    /**
     * 在列表下标位置插入元素
     * 
     * @param key   键
     * @param index 下标
     * @param value 元素
     */
    void listSet(K key, long index, V value);

    /**
     * 删除当前对象
     * 
     * @param key   键
     * @param value 元素
     * @return 列表当前长度
     */
    Long listRemove(K key, V value);

    /**
     * 删除当前对象
     * 
     * @param key   键
     * @param i     记数 (i &gt; 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。
     *              <p>
     *              i &lt; 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。
     *              </p>
     *              <p>
     *              i = 0 : 移除表中所有与 VALUE 相等的值。)
     *              </p>
     * @param value 元素
     * @return 列表当前长度
     */
    Long listRemove(K key, long i, V value);

    /**
     * 获取下标元素
     * 
     * @param key   键
     * @param index 下标
     * @return 元素
     */
    V listIndex(K key, long index);

    /**
     * 抽出列表第一个元素
     * 
     * @param key 键
     * @return 元素
     */
    V listLeftPop(K key);

    /**
     * 抽出列表最后一个元素
     * 
     * @param key 键
     * @return 元素
     */
    V listRightPop(K key);

}
