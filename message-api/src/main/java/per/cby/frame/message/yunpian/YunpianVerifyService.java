package per.cby.frame.message.yunpian;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;

import per.cby.frame.common.result.Result;
import per.cby.frame.message.AbstractVerifyService;
import per.cby.frame.message.VerifyDataCache;

import lombok.extern.slf4j.Slf4j;

/**
 * 云片验证码发送服务
 * 
 * @author chenboyang
 * @since 2019年5月20日
 *
 */
@Slf4j
public class YunpianVerifyService extends AbstractVerifyService {

    @Autowired(required = false)
    private YunpianMessageService yunpianMessageService;

    public YunpianVerifyService(VerifyDataCache verifyDataCache) {
        super(verifyDataCache);
    }

    @Override
    public Result<?> send(String phone, String tplId, String code) {
        String tplVal = null;
        try {
            tplVal = String.format("%s=%s", URLEncoder.encode("#code#", "UTF-8"), URLEncoder.encode(code, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        Result<?> result = yunpianMessageService.send(phone, tplId, tplVal);
        saveCode(phone, code, result);
        return result;
    }

}
