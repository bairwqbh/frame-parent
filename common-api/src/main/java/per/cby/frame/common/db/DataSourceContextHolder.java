package per.cby.frame.common.db;

import java.util.Stack;

import lombok.experimental.UtilityClass;

/**
 * 数据库源开关控制器
 * 
 * @author chenboyang
 *
 */
@UtilityClass
public class DataSourceContextHolder {

    /** 当前数据源标识上下文 */
    private static final ThreadLocal<Stack<Object>> CONTEXT_HOLDER = new ThreadLocal<Stack<Object>>();

    /**
     * 将数据源标识推送到上下文顶部
     * 
     * @param value 数据源标识
     * @return 数据源标识
     */
    public Object set(Object value) {
        if (CONTEXT_HOLDER.get() == null) {
            CONTEXT_HOLDER.set(new Stack<Object>());
        }
        return CONTEXT_HOLDER.get().push(value);
    }

    /**
     * 查看上下文顶部信息
     * 
     * @return 数据源标识
     */
    public Object get() {
        if (CONTEXT_HOLDER.get() == null || CONTEXT_HOLDER.get().empty()) {
            return null;
        }
        return CONTEXT_HOLDER.get().peek();
    }

    /**
     * 删除上下文顶部信息，并将该信息返回
     * 
     * @return 数据源标识
     */
    public Object remove() {
        if (CONTEXT_HOLDER.get() == null || CONTEXT_HOLDER.get().empty()) {
            return null;
        }
        return CONTEXT_HOLDER.get().pop();
    }

    /**
     * 清除当前数据库标识
     */
    public void clear() {
        if (CONTEXT_HOLDER.get() != null) {
            CONTEXT_HOLDER.get().clear();
        }
        CONTEXT_HOLDER.remove();
    }

}
