package per.cby.frame.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import per.cby.frame.common.util.BaseUtil;
import per.cby.frame.redis.constant.RedisConstant;
import per.cby.frame.redis.storage.hash.RedisHashStorage;
import per.cby.frame.web.service.ValidateSignService;

/**
 * 验证签名服务接口实现
 * 
 * @author chenboyang
 * @since 2020年5月6日
 *
 */
public class ValidateSignServiceImpl implements ValidateSignService {

    @Autowired(required = false)
    @Qualifier(RedisConstant.KEY_SECRET_HASH)
    private RedisHashStorage<String, String> keySecretHash;

    @Override
    public String getSecret(String key) {
        return keySecretHash.get(key);
    }

    @Override
    public String genSign(String key, String secret, String timestamp) {
        return BaseUtil.md5Encode(key + secret + timestamp);
    }

}
