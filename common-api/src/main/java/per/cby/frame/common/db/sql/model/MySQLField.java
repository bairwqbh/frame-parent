package per.cby.frame.common.db.sql.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * MySQL字段信息
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
public class MySQLField {

    /** 表名 */
    private String tableName;

    /** 列名 */
    private String columnName;

    /** 旧列名 */
    private String oldColumnName;

    /** 新列名 */
    private String newColumnName;

    /** 数据类型 */
    private String dataType;

    /** 长度 */
    private Integer length;

    /** 精度 */
    private Integer decimal;

    /** 不为空 */
    private String notNull;

    /** 默认值 */
    private String defaultValue;

    /** 描述 */
    private String comment;

    /** 操作 */
    private String operation;

}
