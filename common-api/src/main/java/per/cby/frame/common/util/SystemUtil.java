package per.cby.frame.common.util;

import per.cby.frame.common.config.properties.SysProperties;
import per.cby.frame.common.db.sql.service.MyBatisService;
import per.cby.frame.common.model.cache.SimpleDataCache;
import per.cby.frame.common.sys.service.SysService;
import per.cby.frame.common.sys.storage.SystemStorage;
import per.cby.frame.common.tree.Treeable;
import per.cby.frame.common.tree.TreeableService;

import lombok.experimental.UtilityClass;

/**
 * 系统业务帮助类
 * 
 * @author chenboyang
 * 
 */
@UtilityClass
@SuppressWarnings("unchecked")
public class SystemUtil {

    /**
     * 系统业务属性配置
     * 
     * @return 属性配置
     */
    public SysProperties sysProperties() {
        return SpringContextUtil.getBean(SysProperties.class);
    }

    /**
     * 获取系统存储接口
     * 
     * @return 系统存储接口
     */
    public SystemStorage systemStorage() {
        return SpringContextUtil.getBean(SystemStorage.class);
    }

    /**
     * 获取Mybatis数据访问通用服务
     * 
     * @return Mybatis数据访问通用服务
     */
    public MyBatisService myBatisService() {
        return SpringContextUtil.getBean(MyBatisService.class);
    }

    /**
     * 获取系统服务接口
     * 
     * @return 系统服务接口
     */
    public SysService sysService() {
        return SpringContextUtil.getBean(SysService.class);
    }

    /**
     * 获取树状数据服务接口
     * 
     * @return 树状数据服务接口
     */
    public <T extends Treeable<T>> TreeableService<T> treeableService() {
        return SpringContextUtil.getBean(TreeableService.class);
    }

    /**
     * 获取简单数据缓存
     * 
     * @return 简单数据缓存
     */
    public <K, V> SimpleDataCache<K, V> simpleDataCache() {
        return SpringContextUtil.getBean(SimpleDataCache.class);
    }

}
