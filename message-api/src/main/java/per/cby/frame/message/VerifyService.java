package per.cby.frame.message;

import per.cby.frame.common.result.Result;

/**
 * 验证码发送服务接口
 * 
 * @author chenboyang
 *
 */
public interface VerifyService {

    /**
     * 发送验证码
     * 
     * @param phone 手机号
     * @param tplId 模板编号
     * @param code  验证码
     * @return 发送结果
     */
    Result<?> send(String phone, String tplId, String code);

    /**
     * 检查手机号是否能发送
     * 
     * @param phone 手机号
     * @return 是否能发送
     */
    boolean checkIsSend(String phone);

    /**
     * 检查手机号验证码是否有效
     * 
     * @param phone 手机号
     * @param code  验证码
     * @return 是否有效
     */
    boolean checkValid(String phone, String code);

}
