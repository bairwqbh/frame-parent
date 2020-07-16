package per.cby.frame.common.sys.service;

import org.springframework.beans.factory.annotation.Autowired;

import per.cby.frame.common.config.properties.SysProperties;
import per.cby.frame.common.sys.storage.SystemStorage;

/**
 * 系统服务实现类
 * 
 * @author chenboyang
 *
 */
public class SysServiceImpl implements SysService {

    @Autowired(required = false)
    private SysProperties sysProperties;

    @Autowired(required = false)
    private SystemStorage systemStorage;

    @Override
    public SysProperties sysProperties() {
        return sysProperties;
    }

    @Override
    public SystemStorage systemStorage() {
        return systemStorage;
    }

}
