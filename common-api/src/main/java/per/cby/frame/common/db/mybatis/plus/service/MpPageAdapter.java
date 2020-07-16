package per.cby.frame.common.db.mybatis.plus.service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import per.cby.frame.common.model.Page;

/**
 * MybatisPlus分页适配器
 * 
 * @author chenboyang
 *
 */
public interface MpPageAdapter<T> extends MpPageConverter, IService<T> {

    /**
     * <p>
     * 翻页查询
     * </p>
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类
     */
    default Page<T> page(Page<T> page, Wrapper<T> queryWrapper) {
        return mpToPage(page(pageToMp(page), queryWrapper), page);
    }

    /**
     * <p>
     * 无条件翻页查询
     * </p>
     *
     * @param page 翻页对象
     * @see Wrappers#emptyWrapper()
     */
    default Page<T> page(Page<T> page) {
        return page(page, Wrappers.emptyWrapper());
    }

    /**
     * <p>
     * 翻页查询
     * </p>
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类
     */
    default Page<Map<String, Object>> pageMaps(Page<T> page, Wrapper<T> queryWrapper) {
        return mpToPage(pageMaps(pageToMp(page), queryWrapper));
    }

    /**
     * <p>
     * 无条件翻页查询
     * </p>
     *
     * @param page 翻页对象
     * @see Wrappers#emptyWrapper()
     */
    default Page<Map<String, Object>> pageMaps(Page<T> page) {
        return pageMaps(page, Wrappers.emptyWrapper());
    }

}
