package per.cby.frame.web.exception;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import lombok.experimental.UtilityClass;

/**
 * 异常处理帮助类
 * 
 * @author chenboyang
 *
 */
@UtilityClass
public class WebExceptionUtil {

    /**
     * 在请求信息中获取异常
     * 
     * @param request 请求信息
     * @return 异常
     */
    public Throwable getThrowable(HttpServletRequest request) {
        return (Throwable) Optional.ofNullable(request.getAttribute("exception"))
                .orElse(request.getAttribute("javax.servlet.error.exception"));
    }

}
