package per.cby.frame.common.result;

import lombok.experimental.UtilityClass;

/**
 * 返回结果构建工厂
 * 
 * @author chenboyang
 * 
 */
@UtilityClass
public class ResultFactory {

    /**
     * 获取成功返回结果
     * 
     * @return 返回结果
     */
    public <T> Result<T> success() {
        return create(ResultEnum.SUCCESS);
    }

    /**
     * 获取失败返回结果
     * 
     * @return 返回结果
     */
    public <T> Result<T> fail() {
        return create(ResultEnum.FAIL);
    }

    /**
     * 创建结果对象
     * 
     * @param result 结果信息
     * @return 结果对象
     */
    public <T> Result<T> create(IResult result) {
        return create(result.getCode(), result.getMessage(), result.getData());
    }

    /**
     * 创建结果对象
     * 
     * @param code    返回状态码
     * @param message 返回消息
     * @param data    数据
     * @return 结果对象
     */
    public <T> Result<T> create(int code, String message) {
        return create(code, message, null);
    }

    /**
     * 创建结果对象
     * 
     * @param code    返回状态码
     * @param message 返回消息
     * @param data    数据
     * @return 结果对象
     */
    public <T> Result<T> create(int code, String message, T data) {
        return new Result<T>().setCode(code).setMessage(message).setData(data);
    }

}
