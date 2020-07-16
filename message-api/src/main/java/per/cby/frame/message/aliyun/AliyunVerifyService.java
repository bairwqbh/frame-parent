package per.cby.frame.message.aliyun;

import org.springframework.beans.factory.annotation.Autowired;

import per.cby.frame.common.result.Result;
import per.cby.frame.message.AbstractVerifyService;
import per.cby.frame.message.VerifyDataCache;

/**
 * 阿里云验证码发送服务
 * 
 * @author chenboyang
 * @since 2019年5月17日
 *
 */
public class AliyunVerifyService extends AbstractVerifyService {

    @Autowired(required = false)
    private AliyunMessageService aliyunMessageService;

    public AliyunVerifyService(VerifyDataCache verifyDataCache) {
        super(verifyDataCache);
    }

    @Override
    public Result<?> send(String phone, String tplId, String code) {
        Result<?> result = aliyunMessageService.send(phone, tplId, "{\"code\":\"" + code + "\"}");
        saveCode(phone, code, result);
        return result;
    }

}
