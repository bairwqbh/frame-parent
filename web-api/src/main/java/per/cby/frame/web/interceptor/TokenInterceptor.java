package per.cby.frame.web.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import per.cby.frame.common.result.Result;
import per.cby.frame.common.util.JsonUtil;
import per.cby.frame.web.constant.ResponseResult;
import per.cby.frame.web.constant.WebConstant;
import per.cby.frame.web.session.SessionManager;
import per.cby.frame.web.util.WebUtil;

import lombok.RequiredArgsConstructor;

/**
 * Token访问拦截器
 * 
 * @author chenboyang
 *
 */

@RequiredArgsConstructor
public class TokenInterceptor extends HandlerInterceptorAdapter implements WebConstant {

    /** 会话管理器 */
    private final SessionManager sessionManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = WebUtil.applyService().getToken(request);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(WebConstant.CHARSET);
        response.setHeader(HttpHeaders.CACHE_CONTROL, WebConstant.CACHE_CONTROL);
        if (StringUtils.isEmpty(token)) {
            return error(response, ResponseResult.NO_TOKEN.result());
        }
        if (sessionManager != null && !sessionManager.hasSession(token)) {
            return error(response, ResponseResult.TOKEN_ERROR.result());
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 错误信息封装
     * 
     * @param response 响应信息
     * @param result   异常结果
     * @return 失败
     * @throws IOException IO异常
     */
    private boolean error(HttpServletResponse response, Result<?> result) throws IOException {
        response.getWriter().write(JsonUtil.toJson(result));
        response.getWriter().close();
        return false;
    }

}
