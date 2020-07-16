package per.cby.frame.common.base;

import per.cby.frame.common.util.ReflectUtil;

/**
 * 基础方法
 * 
 * @author chenboyang
 *
 */
@SuppressWarnings("unchecked")
public interface BaseMethod {

    /**
     * 获取属性值
     * 
     * @param name 属性名称
     * @return 属性值
     */
    default <T> T get(String name) {
        return (T) ReflectUtil.getPropertyValue(this, name);
    }

    /**
     * 设置属性值
     * 
     * @param name  属性名称
     * @param value 属性值
     */
    default void set(String name, Object value) {
        ReflectUtil.setPropertyValue(this, name, value);
    }

}
