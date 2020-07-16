package per.cby.frame.common.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import per.cby.frame.common.util.BaseUtil;
import per.cby.frame.common.util.JudgeUtil;

/**
 * 树状数据服务接口实现类
 * 
 * @author chenboyang
 *
 */
public class TreeableServiceImpl<T extends Treeable<T>> implements TreeableService<T> {

    @Override
    public List<T> tree(List<T> list, String... topCodes) {
        List<T> result = null;
        if (CollectionUtils.isNotEmpty(list)) {
            result = new ArrayList<T>();
            Map<String, T> map = new HashMap<String, T>();
            list = new LinkedList<T>(list);
            int size = 0;
            while (list.size() != size) {
                size = list.size();
                Iterator<T> iterator = list.iterator();
                while (iterator.hasNext()) {
                    T tree = iterator.next();
                    map.put(tree.getKey(), tree);
                    if (StringUtils.isBlank(tree.getParentKey()) || JudgeUtil.isOneEqual(tree.getParentKey(), topCodes)) {
                        result.add(tree);
                        iterator.remove();
                        list.remove(tree);
                    } else {
                        if (map.containsKey(tree.getParentKey())) {
                            T parent = map.get(tree.getParentKey());
                            if (parent.getChildren() == null) {
                                parent.setChildren(BaseUtil.newArrayList());
                            }
                            parent.getChildren().add(tree);
                            iterator.remove();
                            list.remove(tree);
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<T> tree(List<T> list) {
        return tree(list, TOP_LEVEL, TOP_CODE);
    }

    @Override
    public List<T> tree(List<T> list, T top) {
        List<T> result = new ArrayList<T>();
        top.setChildren(tree(list, top.getKey()));
        result.add(top);
        return result;
    }

    @Override
    public List<T> fullTree(List<T> list, String... topCodes) {
        List<T> result = null;
        if (CollectionUtils.isNotEmpty(list)) {
            result = new ArrayList<T>();
            Map<String, T> map = new HashMap<String, T>();
            list = new LinkedList<T>(list);
            int size = 0;
            while (list.size() != size) {
                size = list.size();
                Iterator<T> iterator = list.iterator();
                while (iterator.hasNext()) {
                    T tree = iterator.next();
                    map.put(tree.getKey(), tree);
                    if (StringUtils.isBlank(tree.getParentKey()) || JudgeUtil.isOneEqual(tree.getParentKey(), topCodes)) {
                        result.add(tree);
                        iterator.remove();
                        list.remove(tree);
                    } else {
                        if (map.containsKey(tree.getParentKey())) {
                            T parent = map.get(tree.getParentKey());
                            if (parent.getChildren() == null) {
                                parent.setChildren(new ArrayList<T>());
                            }
                            tree.setParent(parent);
                            parent.getChildren().add(tree);
                            iterator.remove();
                            list.remove(tree);
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<T> fullTree(List<T> list) {
        return fullTree(list, TOP_LEVEL, TOP_CODE);
    }

    @Override
    public List<T> fullTree(List<T> list, T top) {
        List<T> result = new ArrayList<T>();
        top.setChildren(fullTree(list, top.getKey()));
        result.add(top);
        return result;
    }

}
