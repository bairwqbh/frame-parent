package per.cby.frame.redis.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.transaction.annotation.Transactional;

import per.cby.frame.common.base.BaseMapper;
import per.cby.frame.redis.storage.hash.RedisHashStorage;
import per.cby.frame.redis.storage.list.RedisListStorage;
import per.cby.frame.redis.storage.set.RedisSetStorage;

/**
 * Redis到数据访问层执行器接口实现类
 * 
 * @author chenboyang
 *
 */
public class RedisToMapperExecutorImpl extends AbstractRedisJobExecutor implements RedisToMapperExecutor {

    /**
     * 获取接口名称
     * 
     * @param clazz 实例类
     * @return 接口名称
     */
    private String getInterfaceName(Class<?> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        if (ArrayUtils.isNotEmpty(interfaces)) {
            return interfaces[0].getTypeName();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <K, V> void addListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper, int batchSize) {
        Set<K> keys = redisHash.keys();
        if (CollectionUtils.isNotEmpty(keys)) {
            List<V> values = redisHash.multiGet(keys);
            if (CollectionUtils.isNotEmpty(values)) {
                int num = mapper.insertBatch(values, batchSize);
                if (num > 0) {
                    redisHash.delete(keys);
                    doubleOperateLog("Redis哈希存储", redisHash.getClass().getName(), "同步新增列表", num, "数据访问接口",
                            getInterfaceName(mapper.getClass()));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <K, V> void modListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper, int batchSize) {
        Set<K> keys = redisHash.keys();
        if (CollectionUtils.isNotEmpty(keys)) {
            List<V> values = redisHash.multiGet(keys);
            if (CollectionUtils.isNotEmpty(values)) {
                int num = mapper.updateBatchById(values, batchSize);
                if (num > 0) {
                    redisHash.delete(keys);
                    doubleOperateLog("Redis哈希存储", redisHash.getClass().getName(), "同步修改列表", num, "数据访问接口",
                            getInterfaceName(mapper.getClass()));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <K, V> void addOrModListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper,
            int batchSize) {
        Set<K> keys = redisHash.keys();
        if (CollectionUtils.isNotEmpty(keys)) {
            List<V> values = redisHash.multiGet(keys);
            if (CollectionUtils.isNotEmpty(values)) {
                int num = mapper.insertOrUpdateBatch(values, batchSize);
                if (num > 0) {
                    redisHash.delete(keys);
                    doubleOperateLog("Redis哈希存储", redisHash.getClass().getName(), "同步新增或修改列表", num, "数据访问接口",
                            getInterfaceName(mapper.getClass()));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T> void addListRedisListToMapper(RedisListStorage<T> redisList, BaseMapper<T> mapper, int batchSize) {
        Long size = redisList.size();
        if (size != null && size.longValue() > 0) {
            List<T> list = new ArrayList<T>();
            while (redisList.size() > 0) {
                Optional.ofNullable(redisList.rightPop()).ifPresent(list::add);
            }
            if (CollectionUtils.isNotEmpty(list)) {
                int num = mapper.insertBatch(list, batchSize);
                if (num > 0) {
                    doubleOperateLog("Redis列表存储", redisList.getClass().getName(), "同步新增列表", num, "数据访问接口",
                            getInterfaceName(mapper.getClass()));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T> void modListRedisListToMapper(RedisListStorage<T> redisList, BaseMapper<T> mapper, int batchSize) {
        Long size = redisList.size();
        if (size != null && size.longValue() > 0) {
            List<T> list = new ArrayList<T>();
            while (redisList.size() > 0) {
                Optional.ofNullable(redisList.rightPop()).ifPresent(list::add);
            }
            if (CollectionUtils.isNotEmpty(list)) {
                int num = mapper.updateBatchById(list, batchSize);
                if (num > 0) {
                    doubleOperateLog("Redis列表存储", redisList.getClass().getName(), "同步修改列表", num, "数据访问接口",
                            getInterfaceName(mapper.getClass()));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T> void addOrModListRedisListToMapper(RedisListStorage<T> redisList, BaseMapper<T> mapper, int batchSize) {
        Long size = redisList.size();
        if (size != null && size.longValue() > 0) {
            List<T> list = new ArrayList<T>();
            while (redisList.size() > 0) {
                Optional.ofNullable(redisList.rightPop()).ifPresent(list::add);
            }
            if (CollectionUtils.isNotEmpty(list)) {
                int num = mapper.insertOrUpdateBatch(list, batchSize);
                if (num > 0) {
                    doubleOperateLog("Redis列表存储", redisList.getClass().getName(), "同步新增修改列表", num, "数据访问接口",
                            getInterfaceName(mapper.getClass()));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T> void addListRedisSetToMapper(RedisSetStorage<T> redisSet, BaseMapper<T> mapper, int batchSize) {
        Long size = redisSet.size();
        if (size != null && size.longValue() > 0) {
            List<T> list = new ArrayList<T>();
            while (redisSet.size() > 0) {
                Optional.ofNullable(redisSet.pop()).ifPresent(list::add);
            }
            if (CollectionUtils.isNotEmpty(list)) {
                int num = mapper.insertBatch(list, batchSize);
                if (num > 0) {
                    doubleOperateLog("Redis集合存储", redisSet.getClass().getName(), "同步新增列表", num, "数据访问接口",
                            getInterfaceName(mapper.getClass()));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T> void modListRedisSetToMapper(RedisSetStorage<T> redisSet, BaseMapper<T> mapper, int batchSize) {
        Long size = redisSet.size();
        if (size != null && size.longValue() > 0) {
            List<T> list = new ArrayList<T>();
            while (redisSet.size() > 0) {
                Optional.ofNullable(redisSet.pop()).ifPresent(list::add);
            }
            if (CollectionUtils.isNotEmpty(list)) {
                int num = mapper.updateBatchById(list, batchSize);
                if (num > 0) {
                    doubleOperateLog("Redis集合存储", redisSet.getClass().getName(), "同步修改列表", num, "数据访问接口",
                            getInterfaceName(mapper.getClass()));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T> void addOrModListRedisSetToMapper(RedisSetStorage<T> redisSet, BaseMapper<T> mapper, int batchSize) {
        Long size = redisSet.size();
        if (size != null && size.longValue() > 0) {
            List<T> list = new ArrayList<T>();
            while (redisSet.size() > 0) {
                Optional.ofNullable(redisSet.pop()).ifPresent(list::add);
            }
            if (CollectionUtils.isNotEmpty(list)) {
                int num = mapper.insertOrUpdateBatch(list, batchSize);
                if (num > 0) {
                    doubleOperateLog("Redis集合存储", redisSet.getClass().getName(), "同步新增修改列表", num, "数据访问接口",
                            getInterfaceName(mapper.getClass()));
                }
            }
        }
    }

}
