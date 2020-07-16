package per.cby.frame.common.db.sql.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import per.cby.frame.common.db.sql.mapper.DDLMapper;

/**
 * MySQL数据库访问通用服务
 * 
 * @author chenboyang
 */
public interface DDLService {

    /**
     * 执行数据库DDL操作接口
     * 
     * @return DDL操作接口
     */
    DDLMapper ddlMapper();

    /**
     * 创建表
     * 
     * @param map 参数
     */
    @Transactional(rollbackFor = Exception.class)
    default void createTable(Map<String, Object> map) {
        ddlMapper().createTable(map);
    }

    /**
     * 删除表
     * 
     * @param map 参数
     */
    @Transactional(rollbackFor = Exception.class)
    default void dropTable(Map<String, Object> map) {
        ddlMapper().dropTable(map);
    }

    /**
     * 显示表信息
     * 
     * @param map 参数
     * @return 表信息
     */
    default Map<Object, Object> showTable(Map<String, Object> map) {
        return ddlMapper().showTable(map);
    }

    /**
     * 重命名表
     * 
     * @param map 参数
     */
    @Transactional(rollbackFor = Exception.class)
    default void renameTable(Map<String, Object> map) {
        ddlMapper().renameTable(map);
    }

    /**
     * 修改表结构
     * 
     * @param map 参数
     */
    @Transactional(rollbackFor = Exception.class)
    default void editTable(Map<String, Object> map) {
        ddlMapper().editTable(map);
    }

    /**
     * 查询表的列信息
     * 
     * @param map 参数
     * @return 列信息集合
     */
    default List<Map<Object, Object>> selectColumns(Map<String, Object> map) {
        return ddlMapper().selectColumns(map);
    }

    /**
     * 创建空间要素表
     * 
     * @param map 参数
     */
    @Transactional(rollbackFor = Exception.class)
    default void createSpatialTable(Map<String, Object> map) {
        ddlMapper().createSpatialTable(map);
    }

}
