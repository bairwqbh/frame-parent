package per.cby.frame.common.sys.service;

import per.cby.frame.common.config.properties.SysProperties;
import per.cby.frame.common.sys.storage.SystemStorage;

/**
 * 系统服务接口
 * 
 * @author chenboyang
 *
 */
public interface SysService {

    /**
     * 获取系统默认信息配置属性
     * 
     * @return 配置属性
     */
    SysProperties sysProperties();

    /**
     * 获取系统默认存储
     * 
     * @return 系统存储
     */
    SystemStorage systemStorage();

}
