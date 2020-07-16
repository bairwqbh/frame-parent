package per.cby.frame.common.tree;

import java.util.List;

/**
 * 树状数据服务接口
 * 
 * @author chenboyang
 *
 */
public interface TreeableService<T extends Treeable<T>> {

    /** 顶级 */
    String TOP_LEVEL = "0";

    /** 顶级代码 */
    String TOP_CODE = "-";

    /**
     * 将列表数据封装成包含根节点的树状数据
     * 
     * @param list     列表
     * @param topCodes 顶级代码集
     * @return 树状数据
     */
    List<T> tree(List<T> list, String... topCodes);

    /**
     * 将列表数据封装成树状数据
     * 
     * @param list 列表
     * @return 树状数据
     */
    List<T> tree(List<T> list);

    /**
     * 将列表数据封装成包含根节点的树状数据
     * 
     * @param list 列表
     * @param top  顶级节点
     * @return 树状数据
     */
    List<T> tree(List<T> list, T top);

    /**
     * 将列表数据封装成包含上级和根节点的树状数据
     * 
     * @param list     列表
     * @param topCodes 顶级代码集
     * @return 树状数据
     */
    List<T> fullTree(List<T> list, String... topCodes);

    /**
     * 将列表数据封装成包含上级的树状数据
     * 
     * @param list 列表
     * @return 树状数据
     */
    List<T> fullTree(List<T> list);

    /**
     * 将列表数据封装成包含上级和根节点的树状数据
     * 
     * @param list 列表
     * @param top  顶级节点
     * @return 树状数据
     */
    List<T> fullTree(List<T> list, T top);

}
