package per.cby.frame.redis.task;

import per.cby.frame.common.base.BaseMapper;
import per.cby.frame.redis.storage.hash.RedisHashStorage;
import per.cby.frame.redis.storage.list.RedisListStorage;
import per.cby.frame.redis.storage.set.RedisSetStorage;

/**
 * Redis到数据访问层执行器接口
 * 
 * @author chenboyang
 *
 */
public interface RedisToMapperExecutor {

    /** 默认批量操作数量 */
    int DEFAULT_BATCH_SIZE = 1000;

    /**
     * 从Redis哈希到关系型数据库新增列表
     * 
     * @param redisHash Redis哈希接口
     * @param mapper    数据访问接口
     */
    default <K, V> void addListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper) {
        addListRedisHashToMapper(redisHash, mapper, DEFAULT_BATCH_SIZE);
    }

    /**
     * 从Redis哈希到关系型数据库新增列表
     * 
     * @param redisHash Redis哈希接口
     * @param mapper    数据访问接口
     * @param batchSize 批次数据操作条数
     */
    <K, V> void addListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper, int batchSize);

    /**
     * 从Redis哈希到关系型数据库修改列表
     * 
     * @param redisHash Redis哈希接口
     * @param mapper    数据访问接口
     */
    default <K, V> void modListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper) {
        modListRedisHashToMapper(redisHash, mapper, DEFAULT_BATCH_SIZE);
    }

    /**
     * 从Redis哈希到关系型数据库修改列表
     * 
     * @param redisHash Redis哈希接口
     * @param mapper    数据访问接口
     * @param batchSize 批次数据操作条数
     */
    <K, V> void modListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper, int batchSize);

    /**
     * 从Redis哈希到关系型数据库新增或修改列表
     * 
     * @param redisHash Redis哈希接口
     * @param mapper    数据访问接口
     */
    default <K, V> void addOrModListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper) {
        addOrModListRedisHashToMapper(redisHash, mapper, DEFAULT_BATCH_SIZE);
    }

    /**
     * 从Redis哈希到关系型数据库新增或修改列表
     * 
     * @param redisHash Redis哈希接口
     * @param mapper    数据访问接口
     * @param batchSize 批次数据操作条数
     */
    <K, V> void addOrModListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper, int batchSize);

    /**
     * 从Redis列表到关系型数据库新增列表
     * 
     * @param redisList Redis列表接口
     * @param mapper    数据访问接口
     */
    default <T> void addListRedisListToMapper(RedisListStorage<T> redisList, BaseMapper<T> mapper) {
        addListRedisListToMapper(redisList, mapper, DEFAULT_BATCH_SIZE);
    }

    /**
     * 从Redis列表到关系型数据库新增列表
     * 
     * @param redisList Redis列表接口
     * @param mapper    数据访问接口
     * @param batchSize 批次数据操作条数
     */
    <T> void addListRedisListToMapper(RedisListStorage<T> redisList, BaseMapper<T> mapper, int batchSize);

    /**
     * 从Redis列表到关系型数据库修改列表
     * 
     * @param redisList Redis列表接口
     * @param mapper    数据访问接口
     */
    default <T> void modListRedisListToMapper(RedisListStorage<T> redisList, BaseMapper<T> mapper) {
        modListRedisListToMapper(redisList, mapper, DEFAULT_BATCH_SIZE);
    }

    /**
     * 从Redis列表到关系型数据库修改列表
     * 
     * @param redisList Redis列表接口
     * @param mapper    数据访问接口
     * @param batchSize 批次数据操作条数
     */
    <T> void modListRedisListToMapper(RedisListStorage<T> redisList, BaseMapper<T> mapper, int batchSize);

    /**
     * 从Redis列表到关系型数据库新增或修改列表
     * 
     * @param redisList Redis列表接口
     * @param mapper    数据访问接口
     */
    default <T> void addOrModListRedisListToMapper(RedisListStorage<T> redisList, BaseMapper<T> mapper) {
        addOrModListRedisListToMapper(redisList, mapper, DEFAULT_BATCH_SIZE);
    }

    /**
     * 从Redis列表到关系型数据库新增或修改列表
     * 
     * @param redisList Redis哈希接口
     * @param mapper    数据访问接口
     * @param batchSize 批次数据操作条数
     */
    <T> void addOrModListRedisListToMapper(RedisListStorage<T> redisList, BaseMapper<T> mapper, int batchSize);

    /**
     * 从Redis集合到关系型数据库新增列表
     * 
     * @param redisSet Redis集合接口
     * @param mapper   数据访问接口
     */
    default <T> void addListRedisSetToMapper(RedisSetStorage<T> redisSet, BaseMapper<T> mapper) {
        addListRedisSetToMapper(redisSet, mapper, DEFAULT_BATCH_SIZE);
    }

    /**
     * 从Redis集合到关系型数据库新增列表
     * 
     * @param redisSet  Redis集合接口
     * @param mapper    数据访问接口
     * @param batchSize 批次数据操作条数
     */
    <T> void addListRedisSetToMapper(RedisSetStorage<T> redisSet, BaseMapper<T> mapper, int batchSize);

    /**
     * 从Redis集合到关系型数据库修改列表
     * 
     * @param redisSet Redis集合接口
     * @param mapper   数据访问接口
     */
    default <T> void modListRedisSetToMapper(RedisSetStorage<T> redisSet, BaseMapper<T> mapper) {
        modListRedisSetToMapper(redisSet, mapper, DEFAULT_BATCH_SIZE);
    }

    /**
     * 从Redis集合到关系型数据库修改列表
     * 
     * @param redisSet  Redis集合接口
     * @param mapper    数据访问接口
     * @param batchSize 批次数据操作条数
     */
    <T> void modListRedisSetToMapper(RedisSetStorage<T> redisSet, BaseMapper<T> mapper, int batchSize);

    /**
     * 从Redis集合到关系型数据库新增修改列表
     * 
     * @param redisSet Redis集合接口
     * @param mapper   数据访问接口
     */
    default <T> void addOrModListRedisSetToMapper(RedisSetStorage<T> redisSet, BaseMapper<T> mapper) {
        addOrModListRedisSetToMapper(redisSet, mapper, DEFAULT_BATCH_SIZE);
    }

    /**
     * 从Redis集合到关系型数据库新增修改列表
     * 
     * @param redisSet  Redis集合接口
     * @param mapper    数据访问接口
     * @param batchSize 批次数据操作条数
     */
    <T> void addOrModListRedisSetToMapper(RedisSetStorage<T> redisSet, BaseMapper<T> mapper, int batchSize);

}
