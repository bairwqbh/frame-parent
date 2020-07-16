package per.cby.frame.web.constant;

import per.cby.frame.common.base.BaseEnumApi;
import per.cby.frame.common.result.IResult;
import per.cby.frame.common.result.ResultBuilder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 验证签名结果枚举
 * 
 * @author chenboyang
 * @since 2020年5月6日
 *
 */
@Getter
@RequiredArgsConstructor
public enum SignResult implements IResult, ResultBuilder, BaseEnumApi {

    SUCCESS(0, "请求成功!"),
    FAIL(1, "请求失败!"),
    MISS_PARAM(101, "缺少必须的参数!"),
    TIME_FORMAT_ERROR(102, "时间戳格式错误!"),
    EXPIRED(103, "请求已过期!"),
    INVALID_KEY(104, "无效的关键标识!"),
    SIGN_ERROR(105, "签名错误!");

    private final int code;
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
