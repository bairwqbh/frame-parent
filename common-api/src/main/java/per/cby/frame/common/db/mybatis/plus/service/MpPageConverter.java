package per.cby.frame.common.db.mybatis.plus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import per.cby.frame.common.model.Page;

/**
 * MybatisPlus分页转换器
 * 
 * @author chenboyang
 *
 */
public interface MpPageConverter {

    /**
     * 基础分页转MybatisPlus分页
     * 
     * @param page 基础分页
     * @return MybatisPlus分页
     */
    default <T> IPage<T> pageToMp(Page<T> page) {
        return pageToMp(page, new com.baomidou.mybatisplus.extension.plugins.pagination.Page<T>());
    }

    /**
     * 基础分页转MybatisPlus分页
     * 
     * @param page  基础分页
     * @param iPage MybatisPlus分页
     * @return MybatisPlus分页
     */
    default <T> IPage<T> pageToMp(Page<T> page, IPage<T> iPage) {
        iPage.setCurrent(page.getPage());
        iPage.setSize(page.getSize());
        iPage.setTotal(Integer.valueOf(page.getTotal()).longValue());
        iPage.setPages(page.getPages());
        iPage.setRecords(page.getList());
        return iPage;
    }

    /**
     * MybatisPlus分页转基础分页
     * 
     * @param iPage MybatisPlus分页
     * @return 基础分页
     */
    default <T> Page<T> mpToPage(IPage<T> iPage) {
        return mpToPage(iPage, new Page<T>());
    }

    /**
     * MybatisPlus分页转基础分页
     * 
     * @param iPage MybatisPlus分页
     * @param page  基础分页
     * @return 基础分页
     */
    default <T> Page<T> mpToPage(IPage<T> iPage, Page<T> page) {
        page.setPage(Long.valueOf(iPage.getCurrent()).intValue());
        page.setSize(Long.valueOf(iPage.getSize()).intValue());
        page.setTotal(Long.valueOf(iPage.getTotal()).intValue());
        page.setPages(Long.valueOf(iPage.getPages()).intValue());
        page.setList(iPage.getRecords());
        return page;
    }

}
