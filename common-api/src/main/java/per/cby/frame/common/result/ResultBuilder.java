package per.cby.frame.common.result;

/**
 * 返回结果构建器
 * 
 * @author chenboyang
 *
 */
public interface ResultBuilder {

    /**
     * 获取当前返回结果
     * 
     * @return 返回结果
     */
    IResult getResult();

    /**
     * 获取状态结果
     * 
     * @return 状态结果
     */
    default <T> Result<T> result() {
        return ResultFactory.create(getResult());
    }

    /**
     * 获取状态结果
     * 
     * @param message 消息
     * @return 状态结果
     */
    default <T> Result<T> message(String message) {
        return result(message, getResult().getData());
    }

    /**
     * 获取状态结果
     * 
     * @param data 数据
     * @return 状态结果
     */
    default <T> Result<T> data(T data) {
        return result(getResult().getMessage(), data);
    }

    /**
     * 获取状态结果
     * 
     * @param message 消息
     * @param data    数据
     * @return 状态结果
     */
    default <T> Result<T> result(String message, T data) {
        return ResultFactory.create(getResult().getCode(), message, data);
    }

}
