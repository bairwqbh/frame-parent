package per.cby.frame.common.result;

import per.cby.frame.common.base.BaseEnumApi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 返回结果枚举
 * 
 * @author chenboyang
 *
 * @param <T> 结果类型
 */
@Getter
@RequiredArgsConstructor
public enum ResultEnum implements IResult, ResultBuilder, BaseEnumApi {

    SUCCESS(0, "成功!"),
    FAIL(1, "失败!");

    /** 返回状态码 */
    private final int code;

    /** 返回消息 */
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
