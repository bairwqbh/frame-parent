package per.cby.frame.web.resolver;

import java.util.Collection;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.HtmlUtils;

import per.cby.frame.common.util.JsonUtil;
import per.cby.frame.common.util.ReflectUtil;
import per.cby.frame.web.annotation.RequestJson;

/**
 * JSON请求参数模型解析器
 * 
 * @author chenboyang
 *
 */
public class RequestJsonResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestJson.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {
        String name = parameter.getParameterAnnotation(RequestJson.class).value();
        String value = HtmlUtils.htmlUnescape(request.getParameter(name));
        Class<?> type = parameter.getParameterType();
        Object object = ReflectUtil.createObject(type);
        WebDataBinder binder = binderFactory.createBinder(request, object, name);
        if (type.isArray()) {
            Class<?> clazz = ReflectUtil.getClass(ReflectUtil.getPropertyType(type, null));
            object = JsonUtil.toObject(value, clazz);
        } else if (Collection.class.isAssignableFrom(type)) {
            Class<?> clazz = ReflectUtil.getClass(ReflectUtil.getPropertyType(type, null));
            object = JsonUtil.toObject(value, clazz);
        } else {
            object = JsonUtil.toObject(value, type);
        }
        return binder.convertIfNecessary(object, type);
    }

}
