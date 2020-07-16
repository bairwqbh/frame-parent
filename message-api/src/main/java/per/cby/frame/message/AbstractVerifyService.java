package per.cby.frame.message;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;

import per.cby.frame.common.result.Result;
import per.cby.frame.common.result.ResultEnum;
import per.cby.frame.message.VerifyDataCache;
import per.cby.frame.message.VerifyService;

import lombok.RequiredArgsConstructor;

/**
 * 验证码发送服务抽象类
 * 
 * @author chenboyang
 *
 */
@RequiredArgsConstructor
public abstract class AbstractVerifyService implements VerifyService {

    /** 验证码过期时间 */
    @Value("${sys.business.verifycode.timeout:120000}")
    private long timeout;

    /** 验证码数据缓存 */
    protected final VerifyDataCache verifyDataCache;

    /**
     * 保存验证码策略
     * 
     * @param phone  手机号
     * @param code   验证码
     * @param result 发送验证码结果
     */
    protected void saveCode(String phone, String code, Result<?> result) {
        if (result != null && Objects.equals(result.getCode(), ResultEnum.SUCCESS.getCode())) {
            verifyDataCache.set(phone, code, timeout, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public boolean checkIsSend(String phone) {
        return !verifyDataCache.has(phone);
    }

    @Override
    public boolean checkValid(String phone, String code) {
        return Objects.equals(code, verifyDataCache.get(phone));
    }

}
