package per.cby.frame.common.result;

/**
 * 返回结果接口
 * 
 * @author chenboyang
 *
 */
public interface IResult {

    /**
     * 获取返回状态码
     * 
     * @return 状态码
     */
    int getCode();

    /**
     * 获取返回消息
     * 
     * @return 消息
     */
    String getMessage();

    /**
     * 获取返回数据
     * 
     * @return 数据
     */
    default <T> T getData() {
        return null;
    }

}
