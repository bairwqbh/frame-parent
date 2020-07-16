package per.cby.frame.web.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpHeaders;

import per.cby.frame.common.constant.FlagString;
import per.cby.frame.web.constant.WebConstant;

/**
 * 前后端分离RESTful接口过滤器
 * 
 * @author chenboyang
 *
 */
public class RestFilter implements Filter, WebConstant {

    /** 允许的头部字段 */
    private String allowHeaders = ALLOWED_HEADERS_VALUE;

    /** 允许的最大时间 */
    private String maxAge = MAX_AGE.toString();

    /**
     * 设置允许的头部字段
     * 
     * @param headers 头部字段集
     * @return 当前过滤器
     */
    public RestFilter allowHeaders(String... headers) {
        if (ArrayUtils.isNotEmpty(headers)) {
            this.allowHeaders += DELIMITER + String.join(DELIMITER, headers);
        }
        return this;
    }

    /**
     * 设置允许的最大时间
     * 
     * @param maxAge 最大时间
     * @return 当前过滤器
     */
    public RestFilter maxAge(long maxAge) {
        if (maxAge > 0) {
            this.maxAge = String.valueOf(maxAge);
        }
        return this;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = null;
        if (request instanceof HttpServletRequest) {
            req = (HttpServletRequest) request;
        }
        HttpServletResponse res = null;
        if (response instanceof HttpServletResponse) {
            res = (HttpServletResponse) response;
        }
        if (req != null && res != null) {
            res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS_VALUE);
            res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, FlagString.TRUE);
            String origin = Optional.ofNullable(req.getHeader(HttpHeaders.ORIGIN))
                    .orElse(req.getHeader(HttpHeaders.REFERER));
            res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, allowHeaders);
            res.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, maxAge);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
