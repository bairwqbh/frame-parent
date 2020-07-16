package per.cby.frame.common.model.cache;

import per.cby.frame.common.model.time.TimeContainerFactory;
import per.cby.frame.common.model.time.TimeMap;
import per.cby.frame.common.util.SystemUtil;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 简单数据缓存
 * 
 * @author chenboyang
 * @since 2019年7月24日
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
@NoArgsConstructor
@AllArgsConstructor
public class SimpleDataCache<K, V> extends AbstractDataCache<K, V> {

    /** 容器名称 */
    private String name = "simple_data_cache";

    /** 监听间隔 */
    private long interval;

    /** 过期时长 */
    private long timeout;

    /**
     * 构建简单数据缓存
     * 
     * @param name 容器名称
     */
    public SimpleDataCache(String name) {
        this.name = name;
    }

    @Override
    protected TimeMap<K, V> timeMap() {
        return SystemUtil.systemStorage().getOrSet(name, () -> {
            if (interval > 0 && timeout > 0) {
                return TimeContainerFactory.createTimeHashMap(interval, timeout);
            }
            return TimeContainerFactory.createTimeHashMap();
        });
    }

}
