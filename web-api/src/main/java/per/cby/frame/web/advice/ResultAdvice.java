package per.cby.frame.web.advice;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import per.cby.frame.common.result.IResult;
import per.cby.frame.common.util.JsonUtil;
import per.cby.frame.web.annotation.NotResultWrap;
import per.cby.frame.web.constant.ResponseResult;

/**
 * MVC接口返回结果统一处理
 * 
 * @author chenboyang
 * @since 2020年5月7日
 *
 */
@RestControllerAdvice
@ConditionalOnWebApplication
public class ResultAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> clazz = returnType.getContainingClass();
        if (returnType.hasMethodAnnotation(NotResultWrap.class) || clazz.isAnnotationPresent(NotResultWrap.class)) {
            return false;
        }
        if (!clazz.isAnnotationPresent(RestController.class) || (!returnType.hasMethodAnnotation(RequestMapping.class)
                && !returnType.hasMethodAnnotation(GetMapping.class)
                && !returnType.hasMethodAnnotation(PostMapping.class)
                && !returnType.hasMethodAnnotation(PutMapping.class)
                && !returnType.hasMethodAnnotation(DeleteMapping.class)
                && !returnType.hasMethodAnnotation(PatchMapping.class))) {
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        if (body instanceof IResult || body instanceof ResponseEntity) {
            return body;
        }
        Object result = ResponseResult.SUCCESS.data(body);
        if (StringHttpMessageConverter.class.equals(selectedConverterType)) {
            return JsonUtil.toJson(result);
        }
        return result;
    }

}
