package per.cby.frame.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 多JSON参数请求模型解析注解
 * 
 * @author chenboyang
 * @since 2019年7月18日
 *
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MultiRequestBody {

    /**
     * JSON对象的属性名称
     * 
     * @return 名称
     */
    String value() default "";

    /**
     * 参数是否必需
     * 
     * @return 是否必需
     */
    boolean required() default true;

}
