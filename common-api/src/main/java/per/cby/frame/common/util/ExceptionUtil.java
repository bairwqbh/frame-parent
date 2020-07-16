package per.cby.frame.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.experimental.UtilityClass;

/**
 * 异常处理帮助类
 * 
 * @author chenboyang
 *
 */
@UtilityClass
@SuppressWarnings("unchecked")
public class ExceptionUtil extends ExceptionUtils {

    /**
     * 将异常转为运行时异常
     * 
     * @param exception 异常
     * @return 运行时异常
     */
    public RuntimeException revRunExc(Exception exception) {
        if (exception instanceof RuntimeException) {
            return (RuntimeException) exception;
        } else {
            return new RuntimeException(exception);
        }
    }

    /**
     * 获取异常堆栈信息字符串
     * 
     * @param throwable 异常
     * @return 堆栈信息
     */
    public String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * 判断异常是否由某些底层的异常引起.
     * 
     * @param exception             异常
     * @param causeExceptionClasses 异常类型集
     * @return 判断结果
     */
    public boolean isCausedBy(Exception exception, Class<? extends Exception>... causeExceptionClasses) {
        Throwable cause = exception.getCause();
        while (cause != null) {
            for (Class<? extends Exception> causeClass : causeExceptionClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }

}
