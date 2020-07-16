package per.cby.frame.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SpringMVC请求参数模型解析注解
 * 
 * @author chenboyang
 *
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestJson {

    /**
     * 参数名称配置
     * 
     * @return 参数名称
     */
    String value() default "json";

}
