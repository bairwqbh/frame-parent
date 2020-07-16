package per.cby.frame.common.db.sql.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Mybatis数据访问通用DAO
 * 
 * @author chenboyang
 */
@Repository("__MAPPER__")
public interface Mapper {

    /**
     * 查询
     * 
     * @param obj 语句
     * @return 结果
     */
    @Select("${_parameter}")
    List<Map<Object, Object>> select(Object obj);

    /**
     * 插入
     * 
     * @param obj 语句
     * @return 记录数
     */
    @Select("${_parameter}")
    int insert(Object obj);

    /**
     * 更新
     * 
     * @param obj 语句
     * @return 记录数
     */
    @Select("${_parameter}")
    int update(Object obj);

    /**
     * 删除
     * 
     * @param obj 语句
     * @return 记录数
     */
    @Select("${_parameter}")
    int delete(Object obj);

    /**
     * 获取当前序列值
     * 
     * @param tableName 表名
     * @param fieldName 字段名
     * @return 序列值
     */
    @Select("SELECT CASE WHEN MAX(CAST(${fieldName} AS SIGNED)) IS NOT NULL THEN MAX(CAST(${fieldName} AS SIGNED)) + 1 ELSE 1 END FROM ${tableName}")
    int currSeq(@Param("tableName") String tableName, @Param("fieldName") String fieldName);

    /**
     * 字段值是否已经存在
     * 
     * @param tableName 表名
     * @param fieldName 字段名
     * @param value     字段值
     * @return 是否存在
     */
    @Select("SELECT CASE WHEN COUNT(1) >= 1 THEN TRUE ELSE FALSE END FROM ${tableName} WHERE ${fieldName} = #{value}")
    boolean isExists(@Param("tableName") String tableName, @Param("fieldName") String fieldName,
            @Param("value") Object value);

}
