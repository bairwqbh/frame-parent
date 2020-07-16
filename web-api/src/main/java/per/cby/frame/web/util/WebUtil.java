package per.cby.frame.web.util;

import per.cby.frame.common.util.SpringContextUtil;
import per.cby.frame.web.service.ApplyService;

import lombok.experimental.UtilityClass;

/**
 * Web业务帮助类
 * 
 * @author chenboyang
 * @since 2020年6月2日
 * 
 */
@UtilityClass
public class WebUtil {

    /**
     * 获取系统应用服务
     * 
     * @return 系统应用服务
     */
    public ApplyService applyService() {
        return SpringContextUtil.getBean(ApplyService.class);
    }

}
