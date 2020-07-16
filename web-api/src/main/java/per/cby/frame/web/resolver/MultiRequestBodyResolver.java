package per.cby.frame.web.resolver;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import per.cby.frame.common.exception.BusinessAssert;
import per.cby.frame.common.util.JsonUtil;
import per.cby.frame.common.util.ReflectUtil;
import per.cby.frame.web.annotation.MultiRequestBody;

import lombok.extern.slf4j.Slf4j;

/**
 * 多JSON参数请求模型解析器
 * 
 * @author chenboyang
 * @since 2019年7月18日
 *
 */
@Slf4j
@SuppressWarnings("unchecked")
public class MultiRequestBodyResolver implements HandlerMethodArgumentResolver {

    /** JSON数据属性名 */
    private final String JSONBODY_ATTRIBUTE = "JSON_REQUEST_BODY";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MultiRequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String json = getBody(webRequest);
        Map<String, Object> map = JsonUtil.toObject(json, Map.class);
        MultiRequestBody multiRequestBody = parameter.getParameterAnnotation(MultiRequestBody.class);
        String name = StringUtils.isNotBlank(multiRequestBody.value()) ? multiRequestBody.value()
                : parameter.getParameterName();
        Class<?> type = parameter.getParameterType();
        Object value = map.get(name);
        if (value != null) {
            if (type.isPrimitive() || ReflectUtil.isWrapClass(type)) {
                return ReflectUtil.cast(type, value);
            }
            if (type == String.class) {
                return value.toString();
            }
            return JsonUtil.toObject(JsonUtil.toJson(value), type);
        }
        BusinessAssert.isTrue(!multiRequestBody.required(), String.format("必需的参数“%s”不存在", name));
        if (type.isPrimitive()) {
            return ReflectUtil.getDefaultValue(type);
        }
        return null;
    }

    /**
     * 获取请求主体数据
     * 
     * @param webRequest 请求信息
     * @return 主体数据
     */
    private String getBody(NativeWebRequest webRequest) {
        String body = (String) webRequest.getAttribute(JSONBODY_ATTRIBUTE, NativeWebRequest.SCOPE_REQUEST);
        if (body == null) {
            try {
                HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
                body = IOUtils.toString(servletRequest.getReader());
                webRequest.setAttribute(JSONBODY_ATTRIBUTE, body, NativeWebRequest.SCOPE_REQUEST);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return body;
    }

}
