package per.cby.frame.common.util;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import per.cby.frame.common.constant.DataType;
import per.cby.frame.common.constant.DefaultValue;

import lombok.experimental.UtilityClass;

/**
 * 默认值设置辅助类
 * 
 * @author chenboyang
 * @since 2019年11月7日
 *
 */
@UtilityClass
@SuppressWarnings("unchecked")
public class DefaultValueUtil {

    /** 数值类型集 */
    private final Class<?>[] NUM_TYPE = { byte.class, Byte.class, short.class, Short.class, int.class, Integer.class,
            long.class, Long.class, float.class, Float.class, double.class, Double.class };

    /**
     * 设置默认值
     * 
     * @param coll 集合
     */
    public void setDefaultValue(Collection<?> coll) {
        if (CollectionUtils.isNotEmpty(coll)) {
            for (Object obj : coll) {
                setDefaultValue(obj);
            }
        }
    }

    /**
     * 设置默认值
     * 
     * @param objs 对象集
     */
    public void setDefaultValue(Object... objs) {
        if (ArrayUtils.isNotEmpty(objs)) {
            for (Object obj : objs) {
                setDefaultValue(obj);
            }
        }
    }

    /**
     * 设置默认值
     * 
     * @param obj 对象
     */
    public void setDefaultValue(Object obj) {
        if (obj == null) {
            return;
        }
        List<Field> fieldList = ReflectUtil.getFields(obj.getClass());
        if (CollectionUtils.isEmpty(fieldList)) {
            return;
        }
        for (Field field : fieldList) {
            if (ReflectUtil.getFieldValue(obj, field.getName()) == null) {
                ReflectUtil.setFieldValue(obj, field.getName(), getDefaultValue(field.getType()));
            }
        }
    }

    /**
     * 根据类型获取默认值
     * 
     * @param type 类型
     * @return 默认值
     */
    public Object getDefaultValue(Class<?> type) {
        switch (judgeType(type)) {
            case NUMBER:
                return ReflectUtil.cast(type, DefaultValue.ZERO);
            case CHAR:
                return ReflectUtil.cast(type, DefaultValue.EMPTY_CHAR);
            case BOOLEAN:
                return DefaultValue.FLASE;
            case STRING:
                return DefaultValue.EMPTY_STRING;
            case LOCAL_DATE:
                return DefaultValue.FIRST_DATE;
            case LOCAL_DATE_TIME:
                return DefaultValue.FIRST_DATE_TIME;
            default:
                return DefaultValue.NULL;
        }
    }

    /**
     * 根据类型获取默认数据类型
     * 
     * @param type 类型
     * @return 默认数据类型
     */
    public DataType judgeType(Class<?> type) {
        if (JudgeUtil.isOneEqual(type, NUM_TYPE)) {
            return DataType.NUMBER;
        } else if (JudgeUtil.isOneEqual(type, char.class, Character.class)) {
            return DataType.CHAR;
        } else if (JudgeUtil.isOneEqual(type, boolean.class, Boolean.class)) {
            return DataType.BOOLEAN;
        } else if (String.class.equals(type)) {
            return DataType.STRING;
        } else if (LocalDate.class.equals(type)) {
            return DataType.LOCAL_DATE;
        } else if (LocalDateTime.class.equals(type)) {
            return DataType.LOCAL_DATE_TIME;
        }
        return DataType.OBJECT;
    }

}
