package per.cby.frame.web.interceptor;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import per.cby.frame.common.result.Result;
import per.cby.frame.common.util.DateUtil;
import per.cby.frame.common.util.JsonUtil;
import per.cby.frame.common.util.JudgeUtil;
import per.cby.frame.common.util.StringUtil;
import per.cby.frame.web.constant.SignResult;
import per.cby.frame.web.constant.ValidateSignConstant;
import per.cby.frame.web.constant.WebConstant;
import per.cby.frame.web.service.ValidateSignService;

import lombok.RequiredArgsConstructor;

/**
 * 验证签名拦截器
 * 
 * @author chenboyang
 * @since 2020年4月29日
 *
 */
@RequiredArgsConstructor
public class ValidateSignInterceptor extends HandlerInterceptorAdapter implements ValidateSignConstant {

    /** 验证签名服务 */
    private final ValidateSignService validateSignService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(WebConstant.CHARSET);
        response.setHeader(HttpHeaders.CACHE_CONTROL, WebConstant.CACHE_CONTROL);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS_VALUE);
        String key = getParam(request, KEY);
        String timestamp = getParam(request, TIMESTAMP);
        String sign = getParam(request, SIGN);
        if (JudgeUtil.isOneBlank(key, timestamp, sign)) {
            return error(response, SignResult.MISS_PARAM.result());
        }
        if (!StringUtil.isMatche(timestamp, DateUtil.DATETIME_REGEX)) {
            return error(response, SignResult.TIME_FORMAT_ERROR.result());
        }
        if (Duration.between(DateUtil.parseLocalDateTime(timestamp), LocalDateTime.now())
                .getSeconds() > EXPIRED_LIMIT) {
            return error(response, SignResult.EXPIRED.result());
        }
        String secret = validateSignService.getSecret(key);
        if (secret == null) {
            return error(response, SignResult.INVALID_KEY.result());
        }
        String genSign = validateSignService.genSign(key, secret, timestamp);
        if (!StringUtils.equals(sign, genSign)) {
            return error(response, SignResult.SIGN_ERROR.result());
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 从请求信息中获取参数
     * 
     * @param request 请求信息
     * @param name    参数名称
     * @return 参数值
     */
    private String getParam(HttpServletRequest request, String name) {
        String param = request.getHeader(name);
        if (StringUtils.isEmpty(param)) {
            param = request.getParameter(name);
        }
        return param;
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
