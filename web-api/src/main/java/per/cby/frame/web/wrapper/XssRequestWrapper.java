package per.cby.frame.web.wrapper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import lombok.extern.slf4j.Slf4j;

/**
 * XSS漏洞防御请求处理器
 * 
 * @author chenboyang
 *
 */
@Slf4j
public class XssRequestWrapper extends HttpServletRequestWrapper {

    /** XSS处理协议 */
    private static Policy policy = null;

    /**
     * 初始化XSS处理协议
     */
    public void init() {
        if (policy == null) {
            try {
                policy = Policy.getInstance(
                        XssRequestWrapper.class.getClassLoader().getResourceAsStream("antisamy-anythinggoes.xml"));
            } catch (PolicyException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * XSS请求包装
     * 
     * @param request 请求信息
     */
    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
        init();
    }

    /**
     * XSS清理
     * 
     * @param value 参数值
     * @return 处理后的参数值
     */
    private String xssClean(String value) {
        AntiSamy antiSamy = new AntiSamy();
        try {
            final CleanResults cr = antiSamy.scan(value, policy);
            return cr.getCleanHTML();
        } catch (ScanException | PolicyException e) {
            log.error(e.getMessage(), e);
        }
        return value;
    }

    @Override
    public String getHeader(String name) {
        String header = super.getHeader(name);
        return header != null ? xssClean(header) : header;
    }

    @Override
    public String getParameter(String name) {
        String param = super.getParameter(name);
        return param != null ? xssClean(param) : param;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (ArrayUtils.isNotEmpty(values)) {
            for (int i = 0; i < values.length; i++) {
                values[i] = xssClean(values[i]);
            }
            return values;
        }
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> paramMap = super.getParameterMap();
        if (MapUtils.isNotEmpty(paramMap)) {
            paramMap.forEach((key, values) -> {
                if (ArrayUtils.isNotEmpty(values)) {
                    for (int i = 0; i < values.length; i++) {
                        values[i] = xssClean(values[i]);
                    }
                }
            });
        }
        return paramMap;
    }

}
