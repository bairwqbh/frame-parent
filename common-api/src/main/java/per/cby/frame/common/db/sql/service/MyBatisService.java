package per.cby.frame.common.db.sql.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import per.cby.frame.common.db.sql.mapper.Mapper;

/**
 * Mybatis数据访问通用服务
 * 
 * @author chenboyang
 */
public interface MyBatisService {

    /**
     * Mybatis通用数据访问接口
     * 
     * @return 数据持久化接口
     */
    Mapper mapper();

    /**
     * 查询
     * 
     * @param sql 语句
     * @return 结果
     */
    default List<Map<Object, Object>> select(String sql) {
        return mapper().select(sql);
    }

    /**
     * 插入
     * 
     * @param sql 语句
     * @return 记录数
     */
    @Transactional(rollbackFor = Exception.class)
    default int insert(String sql) {
        return mapper().insert(sql);
    }

    /**
     * 更新
     * 
     * @param sql 语句
     * @return 记录数
     */
    @Transactional(rollbackFor = Exception.class)
    default int update(String sql) {
        return mapper().update(sql);
    }

    /**
     * 删除
     * 
     * @param sql 语句
     * @return 记录数
     */
    @Transactional(rollbackFor = Exception.class)
    default int delete(String sql) {
        return mapper().delete(sql);
    }

    /**
     * 获取当前序列值
     * 
     * @param tableName 表名
     * @param fieldName 字段名
     * @return 序列值
     */
    default int currSeq(String tableName, String fieldName) {
        return mapper().currSeq(tableName, fieldName);
    }

    /**
     * 字段值是否已经存在
     * 
     * @param tableName 表名
     * @param fieldName 字段名
     * @param value     字段值
     * @return 是否存在
     */
    default boolean isExists(String tableName, String fieldName, Object value) {
        return mapper().isExists(tableName, fieldName, value);
    }

}
