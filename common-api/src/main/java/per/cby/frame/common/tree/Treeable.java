package per.cby.frame.common.tree;

import java.util.List;

/**
 * 可树形化模型接口
 * 
 * @author chenboyang
 * @since 2019年7月29日
 *
 * @param <T> 业务类型
 */
public interface Treeable<T extends Treeable<T>> {

    /**
     * 获取唯一标识
     * 
     * @return 唯一标识
     */
    String getKey();

    /**
     * 获取名称标识
     * 
     * @return 名称标识
     */
    String getLabel();

    /**
     * 获取上级标识
     * 
     * @return 上级标识
     */
    String getParentKey();

    /**
     * 获取上级节点
     * 
     * @return 上级节点
     */
    T getParent();

    /**
     * 设置上级节点
     * 
     * @param parent 上级节点
     * @return 当前结点
     */
    T setParent(T parent);

    /**
     * 获取子集
     * 
     * @return 子集
     */
    List<T> getChildren();

    /**
     * 设置子集
     * 
     * @param children 子集
     * @return 当前结点
     */
    T setChildren(List<T> children);

}
