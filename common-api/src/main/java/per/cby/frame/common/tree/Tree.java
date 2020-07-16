package per.cby.frame.common.tree;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 普通树状数据结构模型
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
public class Tree implements Treeable<Tree> {

    /** 编号 */
    private String id;

    /** 名称 */
    private String name;

    /** 上级编号 */
    private String parentId;

    /** 等级 */
    private Integer level;

    /** 是否展开 */
    private boolean spread;

    /** 上级对象 */
    private Tree parent;

    /** 下级对象列表 */
    private List<Tree> children;

    /**
     * 创建根节点
     * 
     * @param name 根节点名称
     * @return 根节点
     */
    public static Tree createTop(String name) {
        return createTop(name, false);
    }

    /**
     * 创建根节点
     * 
     * @param name   根节点名称
     * @param spread 是否展开
     * @return 根节点
     */
    public static Tree createTop(String name, boolean spread) {
        Tree tree = new Tree();
        tree.setId(TreeableService.TOP_LEVEL);
        tree.setName(name);
        tree.setLevel(0);
        tree.setSpread(spread);
        return tree;
    }

    @Override
    public String getKey() {
        return id;
    }

    @Override
    public String getLabel() {
        return name;
    }

    @Override
    public String getParentKey() {
        return parentId;
    }

}
