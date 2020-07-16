package per.cby.frame.common.exception;

/**
 * 系统业务异常
 * 
 * @author chenboyng
 *
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 业务异常构造函数
     */
    public BusinessException() {
        super();
    }

    /**
     * 带业务信息的业务异常构造函数
     * 
     * @param message 业务信息
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * 带其它异常信息的业务异常构造函数
     * 
     * @param cause 其它异常
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

    /**
     * 带业务信息和其它异常信息的业务异常构造函数
     * 
     * @param message 业务信息
     * @param cause   其它异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
