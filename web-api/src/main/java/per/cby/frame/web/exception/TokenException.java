package per.cby.frame.web.exception;

import per.cby.frame.web.constant.ResponseResult;

/**
 * Token异常
 * 
 * @author chenboyng
 *
 */
public class TokenException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Token异常构造函数
     */
    public TokenException() {
        super();
    }

    /**
     * 带Token异常信息的Token异常构造函数
     * 
     * @param message Token异常信息
     */
    public TokenException(String message) {
        super(message);
    }

    /**
     * 带其它异常信息的Token异常构造函数
     * 
     * @param cause 其它异常
     */
    public TokenException(Throwable cause) {
        super(cause);
    }

    /**
     * 带Token异常信息和其它异常信息的Token异常构造函数
     * 
     * @param message Token异常信息
     * @param cause   其它异常
     */
    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 是否有Token异常
     * 
     * @param expression 表达式
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, ResponseResult.TOKEN_ERROR.getMessage());
    }

    /**
     * 是否有Token异常
     * 
     * @param expression 表达式
     * @param message    异常消息
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new TokenException(message);
        }
    }

}
