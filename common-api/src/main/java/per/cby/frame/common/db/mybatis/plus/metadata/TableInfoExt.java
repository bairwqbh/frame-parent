package per.cby.frame.common.db.mybatis.plus.metadata;

import static java.util.stream.Collectors.joining;

import java.text.MessageFormat;
import java.util.Map;
import java.util.function.Predicate;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import per.cby.frame.common.db.mybatis.annotation.GeometryField;
import per.cby.frame.common.db.mybatis.enums.DbOperate;
import per.cby.frame.common.util.BaseUtil;
import per.cby.frame.common.util.ReflectUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据库表反射信息扩展
 * </p>
 *
 * @author chenboyang
 * @since 2016-01-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class TableInfoExt extends TableInfo {

    /** 几何字段读写映射 */
    private static final Map<DbType, Map<DbOperate, String>> GEOM_MAP = BaseUtil.newHashMap();

    static {
        Map<DbOperate, String> mySqlMap = BaseUtil.newHashMap();
        mySqlMap.put(DbOperate.READ, "ASTEXT({0}) AS {0}");
        mySqlMap.put(DbOperate.WRITE, "GEOMFROMTEXT({0})");
        GEOM_MAP.put(DbType.MYSQL, mySqlMap);
    }

    /**
     * 构建方法
     * 
     * @param tableInfo 表信息
     */
    public TableInfoExt(TableInfo tableInfo) {
        ReflectUtil.copy(tableInfo, this);
    }

    @Override
    public String getLogicDeleteSql(boolean startWithAnd, boolean deleteValue) {
        if (isLogicDelete()) {
            TableFieldInfo field = getFieldList().stream().filter(TableFieldInfo::isLogicDelete).findFirst()
                    .orElseThrow(() -> ExceptionUtils
                            .mpe(String.format("can't find the logicFiled from table {%s}", getTableName())));
            String formatStr = field.isCharSequence() ? "'%s'" : "%s";
            String logicDeleteSql = field.getColumn() + (deleteValue ? EQUALS : EXCLAMATION_MARK + EQUALS)
                    + String.format(formatStr, field.getLogicDeleteValue());
            if (startWithAnd) {
                logicDeleteSql = " AND " + logicDeleteSql;
            }
            return logicDeleteSql;
        }
        return EMPTY;
    }

    @Override
    public String chooseSelect(Predicate<TableFieldInfo> predicate) {
        String sqlSelect = getKeySqlSelect();
        String fieldsSqlSelect = getFieldList().stream().filter(predicate).map(i -> getSqlSelect(i, getDbType()))
                .collect(joining(COMMA));
        if (StringUtils.isNotEmpty(sqlSelect) && StringUtils.isNotEmpty(fieldsSqlSelect)) {
            return sqlSelect + COMMA + fieldsSqlSelect;
        } else if (StringUtils.isNotEmpty(fieldsSqlSelect)) {
            return fieldsSqlSelect;
        }
        return sqlSelect;
    }

    /**
     * 获取 select sql 片段
     *
     * @param tableFieldInfo 数据库表字段反射信息
     * @param dbType         数据库类型
     * @return sql 片段
     */
    private String getSqlSelect(TableFieldInfo tableFieldInfo, DbType dbType) {
        if (isGeometryField(tableFieldInfo)) {
            return wrapperGeometrySql(tableFieldInfo.getColumn(), dbType, DbOperate.READ);
        }
        return tableFieldInfo.getSqlSelect(dbType);
    }

    @Override
    public String getAllInsertSqlPropertyMaybeIf(String prefix) {
        final String newPrefix = prefix == null ? EMPTY : prefix;
        return getKeyInsertSqlProperty(newPrefix, true) + getFieldList().stream()
                .map(t -> getInsertSqlProperty(t, getDbType(), newPrefix)).collect(joining(NEWLINE));
    }

    /**
     * 获取 inset 时候插入值 sql 脚本片段 insert into table (字段) values (值) 位于 "值" 部位
     * 
     * @param tableFieldInfo 数据库表字段反射信息
     * @param dbType         数据库类型
     * @return sql 脚本片段
     */
    private String getInsertSqlProperty(TableFieldInfo tableFieldInfo, DbType dbType, final String prefix) {
        final String newPrefix = prefix == null ? EMPTY : prefix;
        String sqlScript = SqlScriptUtils.safeParam(newPrefix + tableFieldInfo.getEl());
        if (isGeometryField(tableFieldInfo)) {
            sqlScript = wrapperGeometrySql(sqlScript, dbType, DbOperate.WRITE);
        }
        sqlScript += COMMA;
        if (tableFieldInfo.getFieldFill() == FieldFill.INSERT
                || tableFieldInfo.getFieldFill() == FieldFill.INSERT_UPDATE) {
            return sqlScript;
        }
        return convertIf(sqlScript, tableFieldInfo.getProperty(), tableFieldInfo);
    }

    @Override
    public String getAllSqlSet(boolean ignoreLogicDelFiled, String prefix) {
        String newPrefix = prefix == null ? EMPTY : prefix;
        return getFieldList().stream().filter(i -> {
            if (ignoreLogicDelFiled) {
                return !(isLogicDelete() && i.isLogicDelete());
            }
            return true;
        }).map(t -> getSqlSet(newPrefix, t, getDbType())).collect(joining(NEWLINE));
    }

    /**
     * 获取 set sql 片段
     *
     * @param prefix         前缀
     * @param tableFieldInfo 数据库表字段反射信息
     * @param dbType         数据库类型
     * @return sql 脚本片段
     */
    private String getSqlSet(final String prefix, TableFieldInfo tableFieldInfo, DbType dbType) {
        String newPrefix = prefix == null ? EMPTY : prefix;
        String sqlSet = tableFieldInfo.getColumn() + EQUALS;
        if (StringUtils.isNotEmpty(tableFieldInfo.getUpdate())) {
            sqlSet += String.format(tableFieldInfo.getUpdate(), tableFieldInfo.getColumn());
        } else {
            String value = SqlScriptUtils.safeParam(newPrefix + tableFieldInfo.getEl());
            if (isGeometryField(tableFieldInfo)) {
                value = wrapperGeometrySql(value, dbType, DbOperate.WRITE);
            }
            sqlSet += value;
        }
        sqlSet += COMMA;
        if (tableFieldInfo.getFieldFill() == FieldFill.UPDATE
                || tableFieldInfo.getFieldFill() == FieldFill.INSERT_UPDATE) {
            return sqlSet;
        }
        return convertIf(sqlSet, newPrefix + tableFieldInfo.getProperty(), tableFieldInfo);
    }

    /**
     * 转换成 if 标签的脚本片段
     *
     * @param sqlScript      sql 脚本片段
     * @param property       字段名
     * @param tableFieldInfo 数据库表字段反射信息
     * @return if 脚本片段
     */
    private String convertIf(final String sqlScript, final String property, TableFieldInfo tableFieldInfo) {
        if (tableFieldInfo.getFieldStrategy() == FieldStrategy.IGNORED) {
            return sqlScript;
        }
        if (tableFieldInfo.getFieldStrategy() == FieldStrategy.NOT_EMPTY && tableFieldInfo.isCharSequence()) {
            return SqlScriptUtils.convertIf(sqlScript, String.format("%s != null and %s != ''", property, property),
                    false);
        }
        return SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", property), false);
    }

    /**
     * 是否为几何字段
     * 
     * @param tableFieldInfo
     * @return
     */
    private boolean isGeometryField(TableFieldInfo tableFieldInfo) {
        return ReflectUtil.getField(tableFieldInfo.getClazz(), tableFieldInfo.getProperty())
                .getDeclaredAnnotation(GeometryField.class) != null;
    }

    /**
     * 包装几何字段SQL
     * 
     * @param sql       几何字段SQL
     * @param dbType    数据库类型
     * @param dbOperate 数据库操作类型
     * @return 几何字段SQL
     */
    private String wrapperGeometrySql(String sql, DbType dbType, DbOperate dbOperate) {
        if (GEOM_MAP.containsKey(dbType)) {
            return MessageFormat.format(GEOM_MAP.get(dbType).get(dbOperate), sql);
        }
        return sql;
    }

}
