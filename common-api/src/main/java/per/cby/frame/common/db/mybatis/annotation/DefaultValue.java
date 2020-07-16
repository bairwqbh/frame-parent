package per.cby.frame.common.db.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 默认值自动填充
 * 
 * @author chenboyang
 *
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultValue {

    /**
     * 默认值
     * 
     * @return 默认值
     */
    String value();

}
