package per.cby.frame.common.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import per.cby.frame.common.util.SpringContextUtil;

/**
 * MyBatis基础持久化接口
 * 
 * @author chenboyang
 * 
 */
@SuppressWarnings("unchecked")
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /** 默认批次数量 */
    int DEFAULT_BATCH_SIZE = 1000;

    /**
     * 获取日志记录器
     * 
     * @return 记录器
     */
    default Logger logger() {
        return LoggerFactory.getLogger(getClass());
    }

    /**
     * 获取数据库类型
     * 
     * @return 数据库类型
     */
    default DbType getDBType() {
        return Optional.ofNullable(SpringContextUtil.getBean(GlobalConfig.class)).map(GlobalConfig::getDbConfig)
                .map(DbConfig::getDbType).orElse(DbType.OTHER);
    }

    /**
     * 获取当前模型类型
     * 
     * @return 类型
     */
    default Class<T> currentModelClass() {
        Type[] types = getClass().getGenericInterfaces();
        if (ArrayUtils.isNotEmpty(types)) {
            for (int i = 0; i < types.length; i++) {
                Class<?> clazz = (Class<?>) types[i];
                if (BaseMapper.class.isAssignableFrom(clazz)) {
                    return currentModelClass(clazz.getGenericInterfaces());
                }
            }
        }
        return null;
    }

    /**
     * 根据Mapper类型集获取当前模型类型
     * 
     * @param types Mapper类型集
     * @return 类型
     */
    default Class<T> currentModelClass(Type[] types) {
        if (ArrayUtils.isNotEmpty(types)) {
            for (int i = 0; i < types.length; i++) {
                Type type = types[i];
                if (type instanceof ParameterizedType) {
                    ParameterizedType pt = ((ParameterizedType) type);
                    Type[] params = pt.getActualTypeArguments();
                    if (ArrayUtils.isNotEmpty(params)) {
                        for (int j = 0; j < params.length; j++) {
                            Class<?> paramClass = (Class<?>) params[j];
                            if (Model.class.isAssignableFrom(paramClass)) {
                                return (Class<T>) paramClass;
                            }
                        }
                    }
                    type = pt.getRawType();
                }
                if (type instanceof Class) {
                    Class<T> clazz = currentModelClass(((Class<?>) type).getGenericInterfaces());
                    if (clazz != null && Model.class.isAssignableFrom(clazz)) {
                        return clazz;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取批量处理SQL会话
     * 
     * @return SQL会话
     */
    default SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    /**
     * 根据SQL类型获取SQL语句
     * 
     * @param sqlMethod SQL类型
     * @return SQL语句
     */
    default String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    /**
     * 批量新增实例列表
     * 
     * @param list 实例列表
     * @return 操作记录数
     */
    default int insertBatch(List<T> list) {
        return insertBatch(list, DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量新增实例列表
     * 
     * @param list      实例列表
     * @param batchSize 批次数量
     * @return 操作记录数
     */
    default int insertBatch(List<T> list, int batchSize) {
        int num = 0;
        if (CollectionUtils.isEmpty(list)) {
            return num;
        }
        batchExecute(sqlSession -> {
            String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
            for (int i = 0; i < list.size(); i++) {
                sqlSession.insert(sqlStatement, list.get(i));
                if (i >= 1 && i % batchSize == 0) {
                    sqlSession.flushStatements();
                }
            }
            sqlSession.flushStatements();
        });
        return list.size();
    }

    /**
     * 新增或更新实例
     * 
     * @param entity 实例
     * @return 操作记录数
     */
    default int insertOrUpdate(T entity) {
        int num = 0;
        if (entity != null) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                if (!StringUtils.checkValNull(idVal)) {
                    num = updateById(entity);
                }
                if (num <= 0) {
                    num = insert(entity);
                }
            }
        }
        return num;
    }

    /**
     * 批量新增或更新实例列表
     * 
     * @param list 实例列表
     * @return 操作记录数
     */
    default int insertOrUpdateBatch(List<T> list) {
        return insertOrUpdateBatch(list, DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量新增或更新实例列表
     * 
     * @param list      实例列表
     * @param batchSize 批次数量
     * @return 操作记录数
     */
    default int insertOrUpdateBatch(List<T> list, int batchSize) {
        int num = 0;
        if (CollectionUtils.isEmpty(list)) {
            return num;
        }
        Class<?> clazz = currentModelClass();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
        if (tableInfo == null || StringUtils.isEmpty(tableInfo.getKeyProperty())) {
            return num;
        }
        batchExecute(sqlSession -> {
            for (int i = 0; i < list.size(); i++) {
                T t = list.get(i);
                Object idVal = ReflectionKit.getMethodValue(clazz, t, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal)) {
                    String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
                    sqlSession.insert(sqlStatement, t);
                } else {
                    String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
                    MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                    param.put(Constants.ENTITY, t);
                    sqlSession.update(sqlStatement, param);
                }
                if (i >= 1 && i % batchSize == 0) {
                    sqlSession.flushStatements();
                }
            }
            sqlSession.flushStatements();
        });
        return list.size();
    }

    /**
     * 批量更新实例列表，根据实例主键操作
     * 
     * @param list 实例列表
     * @return 操作记录数
     */
    default int updateBatchById(List<T> list) {
        return updateBatchById(list, DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量更新实例列表，根据实例主键操作
     * 
     * @param list      实例列表
     * @param batchSize 批次数量
     * @return 操作记录数
     */
    default int updateBatchById(List<T> list, int batchSize) {
        int num = 0;
        if (CollectionUtils.isEmpty(list)) {
            return num;
        }
        batchExecute(sqlSession -> {
            String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
            for (int i = 0; i < list.size(); i++) {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constants.ENTITY, list.get(i));
                sqlSession.update(sqlStatement, param);
                if (i >= 1 && i % batchSize == 0) {
                    sqlSession.flushStatements();
                }
            }
            sqlSession.flushStatements();
        });
        return list.size();
    }

    /**
     * ibatis日志记录器
     * 
     * @return 日志记录器
     */
    default Log log() {
        return LogFactory.getLog(getClass());
    }

    /**
     * 根据参数查询MAP
     * 
     * @param wrapper 参数封装器
     * @return MAP
     */
    default Map<String, Object> selectMap(Wrapper<T> wrapper) {
        return SqlHelper.getObject(selectMaps(wrapper));
    }

    /**
     * 根据参数查询Obj
     * 
     * @param wrapper 参数封装器
     * @return Obj
     */
    default Object selectObj(Wrapper<T> wrapper) {
        return SqlHelper.getObject(selectObjs(wrapper));
    }

    /**
     * 批量任务执行
     * 
     * @param executor 执行器
     */
    default void batchExecute(Consumer<SqlSession> consumer) {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionBatch();
            consumer.accept(sqlSession);
            sqlSession.clearCache();
        } catch (Throwable e) {
            logger().error("执行批量操作异常：" + e.getMessage(), e);
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

}
