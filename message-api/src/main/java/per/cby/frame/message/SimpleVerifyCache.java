package per.cby.frame.message;

import per.cby.frame.common.model.cache.AbstractDataCache;
import per.cby.frame.common.model.time.TimeContainerFactory;
import per.cby.frame.common.model.time.TimeMap;
import per.cby.frame.common.util.SystemUtil;
import per.cby.frame.message.VerifyDataCache;

/**
 * 验证码简单存储容器
 * 
 * @author chenboyang
 *
 */
public class SimpleVerifyCache extends AbstractDataCache<String, String> implements VerifyDataCache {

    /** 短信验证码发送校验容器 */
    private final String MOBILE_MESSAGE_VERIFY = "mobile_message_verify";

    @Override
    protected TimeMap<String, String> timeMap() {
        return SystemUtil.systemStorage().getOrSet(MOBILE_MESSAGE_VERIFY, TimeContainerFactory::createTimeHashMap);
    }

}
