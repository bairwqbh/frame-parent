package per.cby.frame.common.db.mybatis.util;

import java.lang.reflect.Field;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import per.cby.frame.common.db.mybatis.annotation.AutoId;
import per.cby.frame.common.db.mybatis.annotation.DefaultValue;
import per.cby.frame.common.db.mybatis.enums.NoType;
import per.cby.frame.common.util.IDUtil;
import per.cby.frame.common.util.ReflectUtil;

/**
 * 填充默认值辅助器
 * 
 * @author chenboyang
 * @since 2019年12月30日
 *
 */
public interface FillDefaultHelper {

    /**
     * 填充字段
     * 
     * @param object 对象
     */
    default void fill(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        if (ArrayUtils.isNotEmpty(fields)) {
            for (Field field : fields) {
                if (field.isAnnotationPresent(DefaultValue.class)) {
                    defaultFill(object, field);
                } else if (field.isAnnotationPresent(AutoId.class)) {
                    autoIdFill(object, field);
                }
            }
        }
    }

    /**
     * 默认值填充
     * 
     * @param object 对象
     * @param field  字段
     */
    default void defaultFill(Object object, Field field) {
        Class<?> type = field.getType();
        String name = field.getName();
        Object value = ReflectUtil.getFieldValue(object, name);
        if (value == null || (String.class.equals(type) && StringUtils.isEmpty(value.toString()))) {
            String defaultValue = field.getAnnotation(DefaultValue.class).value();
            ReflectUtil.setFieldValue(object, name, ReflectUtil.cast(type, defaultValue));
        }
    }

    /**
     * 自动编号填充
     * 
     * @param object 对象
     * @param field  字段
     */
    default void autoIdFill(Object object, Field field) {
        Class<?> type = field.getType();
        String name = field.getName();
        Object value = ReflectUtil.getFieldValue(object, name);
        NoType noType = field.getAnnotation(AutoId.class).value();
        if (String.class.equals(type) && (value == null || StringUtils.isEmpty(value.toString()))) {
            switch (noType) {
                case TIME:
                    value = IDUtil.createUniqueTimeId();
                    break;
                case UUID:
                    value = IDUtil.createUUID32();
                    break;
                case UUID16:
                    value = IDUtil.createUUID16();
                    break;
                default:
                    break;
            }
        } else if (Long.class.equals(type) && value == null) {
            switch (noType) {
                case LONG:
                    value = IDUtil.createLongId();
                    break;
                default:
                    break;
            }
        }
        if (value != null) {
            ReflectUtil.setFieldValue(object, name, value);
        }
    }

}
