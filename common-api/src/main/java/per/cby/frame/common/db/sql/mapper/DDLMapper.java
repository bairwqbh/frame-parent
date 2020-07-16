package per.cby.frame.common.db.sql.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 数据库DDL通用接口
 * 
 * @author chenboyang
 */
@Repository("__DDL_MAPPER__")
public interface DDLMapper {

    /**
     * 创建表
     * 
     * @param obj 参数
     */
    void createTable(Object obj);

    /**
     * 删除表
     * 
     * @param obj 参数
     */
    void dropTable(Object obj);

    /**
     * 显示表信息
     * 
     * @param obj 参数
     * @return 表信息
     */
    Map<Object, Object> showTable(Object obj);

    /**
     * 重命名表
     * 
     * @param obj 参数
     */
    void renameTable(Object obj);

    /**
     * 修改表结构
     * 
     * @param obj 参数
     */
    void editTable(Object obj);

    /**
     * 查询表的列信息
     * 
     * @param obj 参数
     * @return 列信息集合
     */
    List<Map<Object, Object>> selectColumns(Object obj);

    /**
     * 创建空间要素表
     * 
     * @param obj 参数
     */
    void createSpatialTable(Object obj);

}
