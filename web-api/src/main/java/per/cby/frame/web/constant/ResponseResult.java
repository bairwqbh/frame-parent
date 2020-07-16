package per.cby.frame.web.constant;

import per.cby.frame.common.base.BaseEnumApi;
import per.cby.frame.common.result.IResult;
import per.cby.frame.common.result.ResultBuilder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 返回结果枚举
 * 
 * @author chenboyang
 *
 */
@Getter
@RequiredArgsConstructor
public enum ResponseResult implements IResult, ResultBuilder, BaseEnumApi {

    SUCCESS(0, "请求成功!"),
    FAIL(1, "请求失败!"),
    NO_TOKEN(2, "无请求token!"),
    TOKEN_ERROR(3, "token错误!"),
    LOGIN_INFO_ERROR(4, "用户密码错误!"),
    VERIFY_CODE_ERROR(5, "验证码错误!"),
    NOT_FOUND(6, "您访问的资源不存在!"),
    SERVER_ERROR(7, "服务器错误!"),
    BUSINESS_ERROR(8, "业务错误!"),
    UNAUTHENTICATED(9, "未通过认证!"),
    INCORRECT_CREDENTIALS(10, "登录密码错误!"),
    EXCESSIVE_ATTEMPTS(11, "登录失败次数过多!"),
    LOCKED_ACCOUNT(12, "帐号已被锁定!"),
    DISABLED_ACCOUNT(13, "帐号已被禁用!"),
    EXPIRED_CREDENTIALS(14, "帐号已过期!"),
    UNKNOWN_ACCOUNT(15, "帐号不存在!"),
    UNAUTHORIZED(16, "您没有相应的授权!"),
    ILLEGAL_ARGUMENT(17, "非法参数!"),
    AUTHENTICATION(18, "认证异常!"),
    INVALID_ROLES(19, "无效角色!"),
    ACCOUNT(20, "用户异常!"),
    CONCURRENT_ACCESS(21, "并发访问!"),
    CREDENTIALS(22, "证书异常!"),
    UNSUPPORTED_TOKEN(23, "不支持的Token!"),
    MISS_REQUEST_PARAM(24, "缺失请求参数!"),
    ARGUMENT_NOT_VALID(25, "参数未通过校验!");

    /** 代码 */
    private final int code;

    /** 描述 */
    private final String message;

    @Override
    public IResult getResult() {
        return this;
    }

    @Override
    public String getDesc() {
        return message;
    }

}
